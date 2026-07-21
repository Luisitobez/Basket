package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.CompetitionEntity


@Dao
interface CompetitionDao {
    @Insert
    suspend fun insert(competition: CompetitionEntity): Long

    @Query("SELECT * FROM competition ORDER BY name")
    fun observeAll(): Flow<List<CompetitionEntity>>
}
