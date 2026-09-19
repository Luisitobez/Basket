package luisitobez.jjvh.basket.domain.rules

import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import javax.inject.Inject

class FoulRules @Inject constructor() {
    val limit: Int = 5
    val technicalLimit: Int = 2

    // --- Faltas personales (ya lo tenías) ---
    fun isEliminated(fouls: Int): Boolean = fouls >= limit

    fun isEliminated(player: PlayerGameStatsModel?): Boolean =
        player != null && isEliminated(player.fouls)

    // --- Faltas técnicas (nuevo, mismo patrón) ---
    fun isEliminatedByTechnicals(technicalFouls: Int): Boolean =
        technicalFouls >= technicalLimit

    fun isEliminatedByTechnicals(player: PlayerGameStatsModel?): Boolean =
        player != null && isEliminatedByTechnicals(player.technicalFouls)

    // --- Un jugador descalificado lo está por cualquiera de las dos ---
    fun isDisqualified(player: PlayerGameStatsModel?): Boolean =
        isEliminated(player) || isEliminatedByTechnicals(player)

    fun canReceiveFoul(player: PlayerGameStatsModel?): RuleResult = when {
        player == null -> RuleResult.Allowed

        isEliminated(player) -> RuleResult.Rejected(
            "El jugador #${player.jerseyNumber} ya tiene ${player.fouls} faltas"
        )

        isEliminatedByTechnicals(player) -> RuleResult.Rejected(
            "El jugador #${player.jerseyNumber} está descalificado por ${player.technicalFouls} faltas técnicas"
        )

        else -> RuleResult.Allowed
    }
}