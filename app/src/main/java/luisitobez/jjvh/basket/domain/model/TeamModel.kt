package luisitobez.jjvh.basket.domain.model

data class TeamModel(
    val id: Long,
    val name: String,
    val shortName: String? = null,
    val logoUri: String? = null
)
