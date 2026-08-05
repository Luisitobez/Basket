package luisitobez.jjvh.basket.ui.Screen.AddGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val teamUseCase: TeamUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(AddGameUiState())
    val uiState: StateFlow<AddGameUiState> = _uiState.asStateFlow()

    init {
        getTeams()
    }

    fun getTeams() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                listOfTeams = teamUseCase.getTeams()
            )
        }
    }

    fun putGame(onback: () -> Unit) {
        viewModelScope.launch {

        }
    }

    fun onChangeCompetitionId(competitionId: Int) {
        _uiState.value = _uiState.value.copy(
            competitionId = competitionId
        )
    }

    fun onChangeHomeTeamId(homeTeamId: Int) {
        _uiState.value = _uiState.value.copy(
            homeTeamId = homeTeamId
        )
    }

    fun onChangeAwayTeamId(awayTeamId: Int) {
        _uiState.value = _uiState.value.copy(
            awayTeamId = awayTeamId
        )
    }

    fun onChangeVenue(venue: String) {
        _uiState.value = _uiState.value.copy(
            venue = venue
        )
    }

    fun onChangeGameDate(gameDate: String) {
        _uiState.value = _uiState.value.copy(
            gameDate = gameDate
        )
    }

    fun onChangeCurrentPeriod(currentPeriod: Int) {
        _uiState.value = _uiState.value.copy(
            currentPeriod = currentPeriod
        )
    }

    fun onChangeClockSecondsRemaining(clockSecondsRemaining: Int) {
        _uiState.value = _uiState.value.copy(
            clockSecondsRemaining = clockSecondsRemaining
        )
    }

    fun onChangeNotes(notes: String) {
        _uiState.value = _uiState.value.copy(
            notes = notes
        )
    }

    fun onChangeExpanded1(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(
            expanded1 = expanded
        )
    }

    fun onChangeExpanded2(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(
            expanded2 = expanded
        )
    }

    fun onChangeShowDatePicker(showDatePicker: Boolean) {
        _uiState.value = _uiState.value.copy(
            showDatePicker = showDatePicker
        )
    }

    fun onChangeDatePickerState(datePickerState: String) {
        _uiState.value = _uiState.value.copy(
            datePickerState = datePickerState,
            gameDate = datePickerState
        )
    }

    fun onChangeStatus(status: String) {
        _uiState.update {
            it.copy(
                status = status
            )
        }
    }

    fun onChangeExpandedStatus(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expandedStatus = expanded
            )
        }
    }

    fun onClickeAddGame() {
        viewModelScope.launch {
            val game = GameModel(
                id = 0,
                competitionId = null,
                homeTeamId = _uiState.value.homeTeamId!!.toLong(),
                awayTeamId = _uiState.value.awayTeamId!!.toLong(),
                venue = _uiState.value.venue,
                gameDate = _uiState.value.gameDate,
                status = _uiState.value.status,
                currentPeriod = _uiState.value.currentPeriod,
                clockSecondsRemaining = null,
                notes = _uiState.value.notes
            )
            gameUseCase.putGame(game)
        }
    }
}

data class AddGameUiState(
    val competitionId: Int = 0,
    val homeTeamId: Int? = null,
    val awayTeamId: Int? = null,
    val venue: String = "",
    val gameDate: String = "",
    val status: String = "",
    val currentPeriod: Int = 0,
    val clockSecondsRemaining: Int = 0,
    val notes: String = "",
    val listOfTeams: Flow<List<TeamModel>> = MutableStateFlow(listOf()),
    val expanded1: Boolean = false,
    val expanded2: Boolean = false,
    val showDatePicker: Boolean = false,
    val datePickerState: String? = null,
    val mapStatus: Map<String, String> = mapOf(
        "SCHEDULED" to "Programado",
        "IN_PROGRESS" to "En progreso",
        "FINISHED" to "Finalizado",
        "CANCELLED" to "Cancelado"
    ),
    val expandedStatus: Boolean = false
)