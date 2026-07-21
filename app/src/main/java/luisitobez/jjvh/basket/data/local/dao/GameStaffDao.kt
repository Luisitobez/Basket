package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import luisitobez.jjvh.basket.data.local.entity.GameStaffEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GameStaffDao {
    @Insert
    suspend fun insertAll(staff: List<GameStaffEntity>)

    @Query("SELECT * FROM game_staff WHERE game_id = :gameId ORDER BY role, full_name")
    fun observeForGame(gameId: Long): Flow<List<GameStaffEntity>>
}
