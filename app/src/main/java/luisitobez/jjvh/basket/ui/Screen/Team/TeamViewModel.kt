package luisitobez.jjvh.basket.ui.Screen.Team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val teamUseCase: TeamUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(TeamUiState())
    val uiState: StateFlow<TeamUiState> = _uiState

    init {
        getTeams()
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

}

data class TeamUiState(
    val teams: List<TeamModel> = emptyList(),
    val team: TeamModel? = null
)