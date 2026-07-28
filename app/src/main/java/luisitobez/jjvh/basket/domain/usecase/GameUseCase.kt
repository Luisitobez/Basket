package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository

class GameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun getGameById(id: Int): Flow<GameModel?> {
        return gameRepository.getGameById(id)
    }

    fun getGames(): Flow<List<GameModel>> {
        return gameRepository.getGames()
    }

    suspend fun putGame(game: GameModel) {
        gameRepository.putGame(game)
    }
}
