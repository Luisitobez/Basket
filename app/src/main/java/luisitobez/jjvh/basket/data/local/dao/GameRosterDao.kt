package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity


@Dao
interface GameRosterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(player: GameRosterEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(players: List<GameRosterEntity>)

    @Update
    suspend fun update(player: GameRosterEntity)

    @Query("SELECT * FROM game_roster WHERE game_id = :gameId AND team_id = :teamId ORDER BY jersey_number")
    fun observeTeamRoster(gameId: Long, teamId: Long): Flow<List<GameRosterEntity>>

    @Query("SELECT * FROM game_roster WHERE id = :rosterId")
    suspend fun getById(rosterId: Long): GameRosterEntity?

    @Query("UPDATE game_roster SET is_active = :isActive WHERE id = :rosterId")
    suspend fun setActive(rosterId: Long, isActive: Boolean)
}
