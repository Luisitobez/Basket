package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.domain.model.GameEventModel
import luisitobez.jjvh.basket.domain.model.GameScoreModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.domain.repository.GameEventRepository
import luisitobez.jjvh.basket.domain.rules.FoulRules
import luisitobez.jjvh.basket.domain.rules.RuleResult
import luisitobez.jjvh.basket.domain.rules.ScoreRules
import javax.inject.Inject

class GameEventUseCase @Inject constructor(
    private val repository: GameEventRepository,
    private val scoreRules: ScoreRules
) {
    fun observeEvents(gameId: Long): Flow<List<GameEventModel>> = repository.observeEvents(gameId)
    fun observeScore(gameId: Long): Flow<GameScoreModel> = repository.observeScore(gameId)
    fun observePlayerStats(gameId: Long): Flow<List<PlayerGameStatsModel>> =
        repository.observePlayerStats(gameId)

    suspend fun record(event: GameEventEntity): Long {
        validate(event)
        require(event.eventType != "FOUL") { "Use recordFoul para registrar faltas" }
        if (isScoringEvent(event)) enforceScoreRules(event)
        return repository.insert(event)
    }

    private fun isScoringEvent(event: GameEventEntity): Boolean =
        event.points > 0 && event.eventType in setOf("SCORE", "FREE_THROW")

    private suspend fun enforceScoreRules(event: GameEventEntity) {
        val rosterId = event.rosterId ?: return
        val stats = repository.observePlayerStats(event.gameId).first()
            .firstOrNull { it.rosterId == rosterId }
        when (val result = scoreRules.canScore(stats, event.points)) {
            is RuleResult.Allowed -> Unit
            is RuleResult.Rejected -> error(result.reason)
        }
    }

    suspend fun recordFoul(event: GameEventEntity): Long {
        validate(event)
        require(event.eventType == "FOUL") { "El evento debe ser FOUL" }
        require(event.teamId != null) { "Una falta requiere equipo" }
        checkNotDisqualified(event)   // 👈 helper común
        return repository.recordFoul(event)
    }

    suspend fun recordTechnicalFoul(event: GameEventEntity): Long {
        validate(event)
        require(event.eventType == "TECHNICAL_FOUL") { "El evento debe ser TECHNICAL_FOUL" }
        require(event.teamId != null) { "Una falta técnica requiere equipo" }
        checkNotDisqualified(event)
        return repository.recordTechnicalFoul(event)
    }

    private suspend fun checkNotDisqualified(event: GameEventEntity) {
        val rosterId = event.rosterId ?: return
        val stats = repository.observePlayerStats(event.gameId).first()
            .firstOrNull { it.rosterId == rosterId }
        when (val result = FoulRules().canReceiveFoul(stats)) {
            is RuleResult.Allowed -> Unit
            is RuleResult.Rejected -> error(result.reason)
        }
    }

    suspend fun cancel(eventId: Long) {
        require(eventId > 0) { "El evento es inválido" }
        repository.cancel(eventId)
    }

    // ---------------------------------------------------------
    // Reglas de negocio
    // ---------------------------------------------------------


    private fun validate(event: GameEventEntity) {
        require(event.gameId > 0 && event.periodNumber > 0) { "Partido o periodo inválido" }
        require(event.eventType.isNotBlank()) { "El tipo de evento es obligatorio" }
        require(event.points >= 0) { "Los puntos no pueden ser negativos" }
        require(event.createdAt.isNotBlank()) { "La fecha de creación es obligatoria" }
    }
}
