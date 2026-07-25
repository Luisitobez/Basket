package luisitobez.jjvh.basket.domain.usecase

import jakarta.inject.Inject
import luisitobez.jjvh.basket.domain.model.gameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository

class GameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend fun getGameByxId(id: Int) {
        gameRepository.getGameById(id)
    }
    operator fun invoke() {
        gameRepository.getGames()
    }
    suspend fun putGame(game: gameModel){
        gameRepository.putGame(game)
    }
}