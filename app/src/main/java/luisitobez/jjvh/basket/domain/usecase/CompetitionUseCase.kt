package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.CompetitionModel
import luisitobez.jjvh.basket.domain.repository.CompetitionRepository
import javax.inject.Inject

class CompetitionUseCase @Inject constructor(private val repository: CompetitionRepository) {
    fun observeAll(): Flow<List<CompetitionModel>> = repository.observeAll()
    suspend fun create(competition: CompetitionModel): Long {
        require(competition.name.isNotBlank()) { "El nombre de la competencia es obligatorio" }
        require(competition.periodsCount > 0 && competition.periodMinutes > 0) { "La configuración de periodos es inválida" }
        return repository.insert(competition)
    }
}
