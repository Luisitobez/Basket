package luisitobez.jjvh.basket.domain.rules

sealed interface RuleResult {
    data object Allowed : RuleResult
    data class Rejected(val reason: String) : RuleResult
}

val RuleResult.isAllowed: Boolean get() = this is RuleResult.Allowed