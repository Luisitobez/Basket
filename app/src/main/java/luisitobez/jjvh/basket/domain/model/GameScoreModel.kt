package luisitobez.jjvh.basket.domain.model

data class GameScoreModel(
    val gameId: Long,
    val homeTeamScore: Int,
    val awayTeamScore: Int
)