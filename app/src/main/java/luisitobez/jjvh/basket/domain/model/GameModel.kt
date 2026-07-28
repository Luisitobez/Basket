package luisitobez.jjvh.basket.domain.model

data class GameModel(
    val id: Long,
    val competitionId: Long?,
    val homeTeamId: Long,
    val awayTeamId: Long,
    val venue: String?,
    val gameDate: String,
    val status: String,
    val currentPeriod: Int,
    val clockSecondsRemaining: Int?,
    val notes: String?
)