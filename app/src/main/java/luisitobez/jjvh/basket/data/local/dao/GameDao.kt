package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.GameEntity

@Dao
interface GameDao {
    @Insert
    suspend fun insert(game: GameEntity): Long

    @Update
    suspend fun update(game: GameEntity)

    @Query("SELECT * FROM game WHERE id = :gameId")
    fun observeById(gameId: Long): Flow<GameEntity?>

    @Query("SELECT * FROM game ORDER BY game_date DESC")
    fun observeAll(): Flow<List<GameEntity>>

    @Query(
        """UPDATE game
        SET current_period = :period,
            clock_seconds_remaining = :secondsRemaining,
            clock_started_at_epoch_ms = :clockStartedAtEpochMs,
            status = :status
        WHERE id = :gameId"""
    )
    suspend fun updateGameClock(
        gameId: Long,
        period: Int,
        secondsRemaining: Int?,
        clockStartedAtEpochMs: Long?,
        status: String = "IN_PROGRESS"
    )

    @Query("UPDATE game SET status = 'FINISHED' WHERE id = :gameId")
    suspend fun finish(gameId: Long)

    @Query("SELECT * FROM game WHERE home_team_id = :teamId OR away_team_id = :teamId ORDER BY game_date DESC")
    fun observeByTeamId(teamId: Long): Flow<List<GameEntity>>

    @Delete(entity = GameEntity::class)
    suspend fun delete(game: GameEntity): Int
}
