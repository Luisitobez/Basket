package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.TeamEntity

@Dao
interface TeamDao {
    @Insert
    suspend fun insert(team: TeamEntity): Long

    @Query("SELECT * FROM team ORDER BY name")
    fun observeAll(): Flow<List<TeamEntity>>

    @Query("SELECT * FROM team WHERE id = :id")
    suspend fun get(id: Long): TeamEntity?

    @Delete(entity = TeamEntity::class)
    suspend fun delete(team: TeamEntity)
}
