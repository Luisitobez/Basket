package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.GameModel

interface GameRepository {
    fun getGameById(id: Int): Flow<GameModel>
    fun getGames(): Flow<List<GameModel>>
    suspend fun putGame(game: GameModel): Flow<GameModel>
}