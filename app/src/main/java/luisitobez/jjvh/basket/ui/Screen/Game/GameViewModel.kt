package luisitobez.jjvh.basket.ui.Screen.Game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    fun getGameById(id: Int) {
        viewModelScope.launch {
            repository.getGameById(id).collect { game ->
                _uiState.value = _uiState.value.copy(
                    game = game
                )
            }


        }
    }

}

data class GameUiState(
    val game: GameModel? = null,
    val homeTeam: String = "",
    val awayTeam: String = "",
    val status: String = "",
    val date: String = "",
    val currentPeriod: Int = 0,
    val clockSecondsRemaining: Int = 0,
    val notes: String = ""
)
