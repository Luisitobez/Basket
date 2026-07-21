package luisitobez.jjvh.basket.data.local.dao

import androidx.room.Dao
import luisitobez.jjvh.basket.data.local.entity.TeamPeriodFoulEntity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TeamPeriodFoulDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(foul: TeamPeriodFoulEntity)

    @Query("SELECT * FROM team_period_foul WHERE game_id = :gameId AND team_id = :teamId AND period_number = :period")
    suspend fun get(gameId: Long, teamId: Long, period: Int): TeamPeriodFoulEntity?

    @Query("SELECT * FROM team_period_foul WHERE game_id = :gameId ORDER BY period_number")
    fun observeForGame(gameId: Long): Flow<List<TeamPeriodFoulEntity>>
}
