package luisitobez.jjvh.basket.data.local.dao

data class PlayerGameStats(
    val gameId: Long,
    val rosterId: Long,
    val teamId: Long,
    val playerName: String,
    val jerseyNumber: Int,
    val points: Int,
    val fouls: Int
)

