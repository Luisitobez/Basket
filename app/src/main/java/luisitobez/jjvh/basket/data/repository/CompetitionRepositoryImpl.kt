package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.CompetitionDao
import luisitobez.jjvh.basket.domain.model.CompetitionModel
import luisitobez.jjvh.basket.domain.repository.CompetitionRepository
import javax.inject.Inject

class CompetitionRepositoryImpl @Inject constructor(
    private val competitionDao: CompetitionDao
) : CompetitionRepository {
    override suspend fun insert(competition: CompetitionModel): Long =
        competitionDao.insert(competition.toEntity())

    override fun observeAll(): Flow<List<CompetitionModel>> =
        competitionDao.observeAll().map { competitions -> competitions.map { it.toDomain() } }
}
