package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.domain.model.GameEventModel
import luisitobez.jjvh.basket.domain.model.GameScoreModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.domain.repository.GameEventRepository
import javax.inject.Inject

class GameEventUseCase @Inject constructor(
    private val repository: GameEventRepository
) {
    fun observeEvents(gameId: Long): Flow<List<GameEventModel>> = repository.observeEvents(gameId)
    fun observeScore(gameId: Long): Flow<GameScoreModel> = repository.observeScore(gameId)
    fun observePlayerStats(gameId: Long): Flow<List<PlayerGameStatsModel>> = repository.observePlayerStats(gameId)

    suspend fun record(event: GameEventEntity): Long {
        validate(event)
        require(event.eventType != "FOUL") { "Use recordFoul para registrar faltas" }
        return repository.insert(event)
    }

    suspend fun recordFoul(event: GameEventEntity): Long {
        validate(event)
        require(event.eventType == "FOUL") { "El evento debe ser FOUL" }
        require(event.teamId != null) { "Una falta requiere equipo" }
        return repository.recordFoul(event)
    }

    suspend fun cancel(eventId: Long) {
        require(eventId > 0) { "El evento es inválido" }
        repository.cancel(eventId)
    }

    private fun validate(event: GameEventEntity) {
        require(event.gameId > 0 && event.periodNumber > 0) { "Partido o periodo inválido" }
        require(event.eventType.isNotBlank()) { "El tipo de evento es obligatorio" }
        require(event.points >= 0) { "Los puntos no pueden ser negativos" }
        require(event.createdAt.isNotBlank()) { "La fecha de creación es obligatoria" }
    }
}
