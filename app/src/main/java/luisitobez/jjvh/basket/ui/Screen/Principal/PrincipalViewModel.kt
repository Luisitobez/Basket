package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import luisitobez.jjvh.basket.domain.model.gameModel
import luisitobez.jjvh.basket.domain.usecase.GameUseCase

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
): ViewModel() {
    suspend fun getGameById(id: Int) {
        gameUseCase.getGameByxId(id)
    }

    fun getGames() {
        gameUseCase.invoke()
    }

    suspend fun putGame(game: gameModel) {
        gameUseCase.putGame(game)
    }
}