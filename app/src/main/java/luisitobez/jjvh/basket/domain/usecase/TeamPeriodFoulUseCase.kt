package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.TeamPeriodFoulModel
import luisitobez.jjvh.basket.domain.repository.TeamPeriodFoulRepository
import javax.inject.Inject

class TeamPeriodFoulUseCase @Inject constructor(private val repository: TeamPeriodFoulRepository) {
    fun observeForGame(gameId: Long): Flow<List<TeamPeriodFoulModel>> = repository.observeForGame(gameId)
    suspend fun get(gameId: Long, teamId: Long, period: Int) = repository.get(gameId, teamId, period)
    suspend fun save(foul: TeamPeriodFoulModel) {
        require(foul.gameId > 0 && foul.teamId > 0 && foul.periodNumber > 0 && foul.foulCount >= 0) { "Conteo de faltas inválido" }
        repository.save(foul)
    }
}
