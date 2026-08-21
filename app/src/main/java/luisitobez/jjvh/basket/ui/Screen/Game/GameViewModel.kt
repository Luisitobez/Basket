package luisitobez.jjvh.basket.ui.Screen.Game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.repository.GameRepository
import luisitobez.jjvh.basket.domain.usecase.GameRosterUseCase
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val gameRosterUseCase: GameRosterUseCase,
    private val teamUseCase: TeamUseCase,
) : ViewModel() {

    val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    fun getGameById(id: Int) {
        viewModelScope.launch {

            gameUseCase.getGameById(id)
                .collect { game ->

                    _uiState.update {
                        it.copy(
                            gameId = id,
                            game = game
                        )
                    }

                    getGameRoster()
                }
        }
    }

    fun getGameRoster() {

        viewModelScope.launch {
            gameRosterUseCase.getAllOfGame(_uiState.value.gameId.toLong()).collect { roster ->
                _uiState.update {
                    it.copy(
                        roostersHomeTeam = roster.filter { roster -> roster.teamId == _uiState.value.game?.homeTeamId },
                        roostersAwayTeam = roster.filter { roster -> roster.teamId == _uiState.value.game?.awayTeamId }
                    )
                }
            }
        }
    }

    fun onChangeTeams() {
        _uiState.update {
            it.copy(
                homeTeam = _uiState.value.listOfTeams.find { team -> team.id.toInt() == _uiState.value.homeTeamId }?.name
                    ?: "",
                awayTeam = _uiState.value.listOfTeams.find { team -> team.id.toInt() == _uiState.value.awayTeamId }?.name
                    ?: ""
            )
        }
    }

    fun onChangeNotes(notes: String) {
        _uiState.update {
            it.copy(
                notes = notes
            )
        }
    }

    fun onGetListOfTeams() {
        viewModelScope.launch {
            teamUseCase.getTeams().collect { teams ->
                _uiState.update {
                    it.copy(
                        listOfTeams = teams
                    )
                }
                _uiState.update {
                    it.copy(
                        awayTeamId = _uiState.value.game?.awayTeamId?.toInt(),
                        homeTeamId = _uiState.value.game?.homeTeamId?.toInt(),
                        venue = _uiState.value.game?.venue ?: "",
                        date = _uiState.value.game?.gameDate ?: "",
                    )
                }
            }
        }
    }

    fun onChangeExpanded1(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expanded1 = expanded
            )
        }
    }

    fun onChangeExpanded2(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expanded2 = expanded
            )
        }
    }

    fun onChangeHomeTeamId(homeTeamId: Int?) {
        _uiState.update {
            it.copy(
                homeTeamId = homeTeamId
            )
        }
    }

    fun onChangeAwayTeamId(awayTeamId: Int?) {
        _uiState.update {
            it.copy(
                awayTeamId = awayTeamId
            )
        }
    }

    fun onChangeVenue(venue: String) {
        _uiState.update {
            it.copy(
                venue = venue
            )
        }
    }

    fun onChangeStatus(status: String) {
        _uiState.update {
            it.copy(
                status = status
            )
        }
    }

    fun onChangeDatePickerState(datePickerState: String) {
        _uiState.update {
            it.copy(
                datePickerState = datePickerState
            )
        }
    }

    fun onChangeShowDatePicker(showDatePicker: Boolean) {
        _uiState.update {
            it.copy(
                showDatePicker = showDatePicker
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

    fun onChangeTeam(team: Boolean) {
        _uiState.update {
            it.copy(
                team = team
            )
        }
    }

    fun onChangePlayerName(playerName: String) {
        _uiState.update {
            it.copy(
                playerName = playerName
            )
        }
    }

    fun onChangeJerseyNumber(jerseyNumber: String) {
        _uiState.update {
            it.copy(
                jerseyNumber = jerseyNumber
            )
        }
    }

    fun onChangeAddPlayer(addPlayer: Boolean) {
        _uiState.update {
            it.copy(
                addPlayer = addPlayer
            )
        }
    }

    fun agregarJugador() {
        viewModelScope.launch {
            gameRosterUseCase.agregarJugador(
                _uiState.value.gameId.toLong(),
                if (_uiState.value.team) _uiState.value.game?.homeTeamId
                    ?: 0L else _uiState.value.game?.awayTeamId ?: 0L,
                _uiState.value.playerName ?: "",
                _uiState.value.jerseyNumber ?: ""
            )
        }
    }

    fun eliminarJugador(rosterId: Long) {
        viewModelScope.launch {
            gameRosterUseCase.eliminarJugador(rosterId)
        }
    }
}

data class GameUiState(
    val gameId: Int = 0,
    val game: GameModel? = null,
    val homeTeam: String = "",
    val homeTeamId: Int? = null,
    val awayTeamId: Int? = null,
    val awayTeam: String = "",
    val status: String = "",
    val date: String = "",
    val currentPeriod: Int = 0,
    val clockSecondsRemaining: Int = 0,
    val notes: String = "",
    val roostersHomeTeam: List<GameRosterModel> = emptyList(),
    val roostersAwayTeam: List<GameRosterModel> = emptyList(),
    val listOfTeams: List<TeamModel> = emptyList(),
    val expanded1: Boolean = false,
    val expanded2: Boolean = false,
    val showDatePicker: Boolean = false,
    val datePickerState: String? = null,
    val venue: String = "",
    val mapStatus: Map<String, String> = mapOf
        (
        "SCHEDULED" to "Programado",
        "IN_PROGRESS" to "En progreso",
        "FINISHED" to "Finalizado",
        "CANCELLED" to "Cancelado"
    ),
    val expandedStatus: Boolean = false,
    val team: Boolean = true,
    val playerName: String? = null,
    val jerseyNumber: String? = null,
    val addPlayer: Boolean = false
)
