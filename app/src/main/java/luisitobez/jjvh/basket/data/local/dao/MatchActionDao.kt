package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.data.local.entity.TeamPeriodFoulEntity

/** Operaciones compuestas que deben ejecutarse como una sola transacción. */
@Dao
abstract class MatchActionDao {
    @Insert
    protected abstract suspend fun insertEvent(event: GameEventEntity): Long

    @Query("SELECT COALESCE(MAX(sequence_number), 0) + 1 FROM game_event WHERE game_id = :gameId")
    protected abstract suspend fun nextSequence(gameId: Long): Int

    @Query("SELECT * FROM game_roster WHERE id = :rosterId")
    protected abstract suspend fun rosterById(rosterId: Long): GameRosterEntity?

    @Query("SELECT foul_count FROM team_period_foul WHERE game_id = :gameId AND team_id = :teamId AND period_number = :period")
    protected abstract suspend fun foulCount(gameId: Long, teamId: Long, period: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun saveTeamFoul(value: TeamPeriodFoulEntity)

    /** Registra cualquier evento y asigna el consecutivo automáticamente. */
    @Transaction
    open suspend fun record(event: GameEventEntity): Long =
        insertEvent(event.copy(sequenceNumber = nextSequence(event.gameId)))

    /** Registra una falta individual y aumenta el contador colectivo del periodo. */
    /*@Transaction
    open suspend fun recordFoul(event: GameEventEntity): Long {
        require(event.eventType == "FOUL") { "El evento debe ser FOUL" }
        val teamId = requireNotNull(event.teamId) { "Una falta requiere equipo" }
        val rosterId = event.rosterId
        if (rosterId != null) {
            val player = requireNotNull(rosterById(rosterId)) { "Jugador inexistente" }
            require(player.gameId == event.gameId && player.teamId == teamId) { "El jugador no pertenece a este equipo/partido" }
        }
        val eventId = record(event)
        val previous = foulCount(event.gameId, teamId, event.periodNumber) ?: 0
        saveTeamFoul(TeamPeriodFoulEntity(event.gameId, teamId, event.periodNumber, previous + 1))
        return eventId
    }*/

    @Transaction
    open suspend fun recordFoul(event: GameEventEntity): Long {
        require(event.eventType == "FOUL") { "El evento debe ser FOUL" }
        return recordTeamFoul(event)
    }

    @Transaction
    open suspend fun recordTechnicalFoul(event: GameEventEntity): Long {
        require(event.eventType == "TECHNICAL_FOUL") { "El evento debe ser TECHNICAL_FOUL" }
        return recordTeamFoul(event)
    }

    private suspend fun recordTeamFoul(event: GameEventEntity): Long {
        val teamId = requireNotNull(event.teamId) { "Una falta requiere equipo" }
        event.rosterId?.let { rosterId ->
            val player = requireNotNull(rosterById(rosterId)) { "Jugador inexistente" }
            require(player.gameId == event.gameId && player.teamId == teamId) {
                "El jugador no pertenece a este equipo/partido"
            }
        }
        val eventId = record(event)
        val previous = foulCount(event.gameId, teamId, event.periodNumber) ?: 0
        saveTeamFoul(
            TeamPeriodFoulEntity(event.gameId, teamId, event.periodNumber, previous + 1)
        )
        return eventId
    }
}
