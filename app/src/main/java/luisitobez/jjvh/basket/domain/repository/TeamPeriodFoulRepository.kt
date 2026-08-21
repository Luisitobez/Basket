package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.TeamPeriodFoulModel

interface TeamPeriodFoulRepository {
    suspend fun save(foul: TeamPeriodFoulModel)
    suspend fun get(gameId: Long, teamId: Long, period: Int): TeamPeriodFoulModel?
    fun observeForGame(gameId: Long): Flow<List<TeamPeriodFoulModel>>
}
