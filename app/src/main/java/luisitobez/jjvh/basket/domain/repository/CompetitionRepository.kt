package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.CompetitionModel

interface CompetitionRepository {
    suspend fun insert(competition: CompetitionModel): Long
    fun observeAll(): Flow<List<CompetitionModel>>
}
