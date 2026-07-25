package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.gameModel
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
): ViewModel() {

    val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    suspend fun getGameById(id: Int) {
        viewModelScope.launch {
            _uiState.value = GameUiState(game = gameUseCase.getGameByxId(id))
        }

    }

    fun getGames() {
        gameUseCase.invoke()
    }

    suspend fun putGame(game: gameModel) {
        gameUseCase.putGame(game)
    }
}

data class GameUiState(
    val game: gameModel? = null,
    val games: List<gameModel>? = null
)