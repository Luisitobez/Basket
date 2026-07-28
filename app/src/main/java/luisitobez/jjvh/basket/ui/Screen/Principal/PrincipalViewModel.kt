package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
): ViewModel() {

    val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    init {
        viewModelScope.launch {
            gameUseCase.getGames().collect { games ->
                _uiState.value = _uiState.value.copy(
                    games = games
                )
            }
        }
    }

    fun getGameById(id: Int) {
        viewModelScope.launch {
            gameUseCase.getGameById(id).collect { game ->
                _uiState.value = _uiState.value.copy(
                    game = game
                )
            }
        }
    }



    suspend fun putGame(game: GameModel) {
        gameUseCase.putGame(game)
    }
}

data class GameUiState(
    val game: GameModel? = null,
    val games: List<GameModel> = emptyList()
)