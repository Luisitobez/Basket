package luisitobez.jjvh.basket.domain.usecase

import luisitobez.jjvh.basket.domain.model.gameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository

class GameUseCase(private val gameRepository: GameRepository) {
    suspend fun getGameByxId(id: Int) {
        gameRepository.getGameById(id)
    }
    suspend fun getGames() {
        gameRepository.getGames()
    }
    suspend fun putGame(game: gameModel){
        gameRepository.putGame(game)
    }
}