package luisitobez.jjvh.basket.domain.model

data class CompetitionModel(
    val id: Long = 0,
    val name: String,
    val category: String? = null,
    val ruleset: String = "FIBA",
    val periodsCount: Int = 4,
    val periodMinutes: Int = 10,
    val overtimeMinutes: Int = 5
)
