package luisitobez.jjvh.basket.domain.model

data class PlayerGameStatsModel(
    val gameId: Long,
    val rosterId: Long,
    val teamId: Long,
    val playerName: String,
    val jerseyNumber: Int,
    val points: Int,
    val fouls: Int
)