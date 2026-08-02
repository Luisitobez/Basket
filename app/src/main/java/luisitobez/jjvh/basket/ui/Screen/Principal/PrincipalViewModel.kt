package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val teamUseCase: TeamUseCase
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

    fun getTeams() {
        viewModelScope.launch {
            teamUseCase.getTeams().collect { teams ->
                _uiState.value = _uiState.value.copy(
                    teams = teams
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
    val games: List<GameModel> = emptyList(),
    val teams: List<TeamModel> = emptyList()
)