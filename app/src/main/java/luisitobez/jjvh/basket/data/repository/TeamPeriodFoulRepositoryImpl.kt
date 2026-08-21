package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.TeamPeriodFoulDao
import luisitobez.jjvh.basket.domain.model.TeamPeriodFoulModel
import luisitobez.jjvh.basket.domain.repository.TeamPeriodFoulRepository
import javax.inject.Inject

class TeamPeriodFoulRepositoryImpl @Inject constructor(
    private val teamPeriodFoulDao: TeamPeriodFoulDao
) : TeamPeriodFoulRepository {
    override suspend fun save(foul: TeamPeriodFoulModel) = teamPeriodFoulDao.save(foul.toEntity())
    override suspend fun get(gameId: Long, teamId: Long, period: Int) =
        teamPeriodFoulDao.get(gameId, teamId, period)?.toDomain()
    override fun observeForGame(gameId: Long): Flow<List<TeamPeriodFoulModel>> =
        teamPeriodFoulDao.observeForGame(gameId).map { fouls -> fouls.map { it.toDomain() } }
}
