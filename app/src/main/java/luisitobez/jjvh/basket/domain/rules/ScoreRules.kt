package luisitobez.jjvh.basket.domain.rules

import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import javax.inject.Inject

class ScoreRules @Inject constructor(
    private val foulRules: FoulRules
) {
    fun canScore(player: PlayerGameStatsModel?, points: Int): RuleResult = when {
        points <= 0 ->
            RuleResult.Rejected("Los puntos deben ser mayores a 0")

        player == null ->
            RuleResult.Rejected("Debes seleccionar un jugador para anotar")

        foulRules.isEliminated(player) ->
            RuleResult.Rejected(
                "El jugador #${player.jerseyNumber} tiene ${player.fouls} faltas y no puede anotar"
            )

        foulRules.isEliminatedByTechnicals(player) ->
            RuleResult.Rejected(
                "El jugador #${player.jerseyNumber} está descalificado por ${player.technicalFouls} faltas técnicas"
            )

        else -> RuleResult.Allowed
    }
}