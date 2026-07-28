package luisitobez.jjvh.basket.ui.Screen.AddTeam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class AddTeamViewModel @Inject constructor(
    private val teamUseCase: TeamUseCase
) : ViewModel() {

    val _uiState = MutableStateFlow(AddTeamUiState())
    val uiState: StateFlow<AddTeamUiState> = _uiState.asStateFlow()

    fun putTeam(onback: () -> Unit) {
        viewModelScope.launch {

            if(teamUseCase.putTeam(
                name = uiState.value.name,
                shortName = uiState.value.shortName,
                uri = uiState.value.uri
            )){
                onback()
            }
        }
    }

    fun onChangeName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name
        )
    }

    fun onChangeShortName(shortName: String) {
        _uiState.value = _uiState.value.copy(
            shortName = shortName
        )
    }
}

data class AddTeamUiState(
    val id: Int = 0,
    val name: String = "",
    val shortName: String = "",
    val uri: String = ""
)