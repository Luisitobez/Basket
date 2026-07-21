package luisitobez.jjvh.basket.domain.repository

interface GameRepository {
    fun getGameById(id: Int): Game?
    fun getGames(): List<Game>
}