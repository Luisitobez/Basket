package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.gameModel

interface GameRepository {
    suspend fun getGameById(id: Int): Flow<gameModel>
    fun getGames(): Flow<List<gameModel>>
    suspend fun putGame(game: gameModel): Flow<gameModel>
}