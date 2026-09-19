package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameScoreView

@Dao
interface GameEventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(event: GameEventEntity): Long

    @Query("SELECT COALESCE(MAX(sequence_number), 0) + 1 FROM game_event WHERE game_id = :gameId")
    suspend fun getNextSequence(gameId: Long): Int

    @Query("SELECT * FROM game_event WHERE game_id = :gameId ORDER BY sequence_number DESC")
    fun observeEvents(gameId: Long): Flow<List<GameEventEntity>>

    @Query("UPDATE game_event SET is_cancelled = 1 WHERE id = :eventId")
    suspend fun cancel(eventId: Long)

    @Query("SELECT * FROM v_game_score WHERE game_id = :gameId")
    fun observeScore(gameId: Long): Flow<GameScoreView?>

    /** Punto/faltas de cada jugador, calculados a partir de los eventos no anulados. */
    @Query(
        """SELECT r.game_id AS gameId, r.id AS rosterId, r.team_id AS teamId,
        r.player_name AS playerName, r.jersey_number AS jerseyNumber,
        COALESCE(SUM(CASE WHEN e.is_cancelled = 0 THEN e.points ELSE 0 END), 0) AS points,
        COALESCE(SUM(CASE WHEN e.is_cancelled = 0 AND e.event_type = 'FOUL' THEN 1 ELSE 0 END), 0) AS fouls,
        COALESCE(SUM(CASE WHEN e.is_cancelled = 0 AND e.event_type = 'TECHNICAL_FOUL' THEN 1 ELSE 0 END), 0) AS technicalFouls
    FROM game_roster r LEFT JOIN game_event e ON e.roster_id = r.id
    WHERE r.game_id = :gameId
    GROUP BY r.id ORDER BY r.team_id, r.jersey_number"""
    )
    fun observePlayerStats(gameId: Long): Flow<List<PlayerGameStats>>

}
