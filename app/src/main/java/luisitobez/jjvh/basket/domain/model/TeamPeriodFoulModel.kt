package luisitobez.jjvh.basket.domain.model

data class TeamPeriodFoulModel(
    val gameId: Long,
    val teamId: Long,
    val periodNumber: Int,
    val foulCount: Int = 0
)
