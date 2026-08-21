package luisitobez.jjvh.basket.domain.model

data class GameStaffModel(
    val id: Long = 0,
    val gameId: Long,
    val teamId: Long? = null,
    val role: String,
    val fullName: String
)
