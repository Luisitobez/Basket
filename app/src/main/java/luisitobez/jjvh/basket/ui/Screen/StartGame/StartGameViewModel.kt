package luisitobez.jjvh.basket.ui.Screen.StartGame

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.domain.model.TeamPeriodFoulModel
import luisitobez.jjvh.basket.domain.usecase.GameEventUseCase
import luisitobez.jjvh.basket.domain.usecase.GameRosterUseCase
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamPeriodFoulUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class StartGameViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val gameRosterUseCase: GameRosterUseCase,
    private val gameEventUseCase: GameEventUseCase,
    private val teamPeriodFoulUseCase: TeamPeriodFoulUseCase,
    private val teamUseCase: TeamUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartGameUiState())
    val uiState: StateFlow<StartGameUiState> = _uiState.asStateFlow()

    private var gameJob: Job? = null
    private var rosterJob: Job? = null
    private var scoreJob: Job? = null
    private var statsJob: Job? = null
    private var foulsJob: Job? = null
    private var clockJob: Job? = null
    private var eventsJob: Job? = null

    fun loadGame(gameId: Int) {
        if (gameId <= 0 || (_uiState.value.idGame == gameId && gameJob?.isActive == true)) return
        cancelObservers()
        _uiState.value = StartGameUiState(idGame = gameId, isLoading = true)

        gameJob = viewModelScope.launch {
            gameUseCase.getGameById(gameId).collect { game ->
                val loadedGame = game ?: run {
                    setError("No se encontró el partido")
                    return@collect
                }
                val homeName =
                    runCatching { teamUseCase.getNameByTeamId(loadedGame.homeTeamId.toInt()) }.getOrDefault(
                        "Local"
                    )
                val awayName =
                    runCatching { teamUseCase.getNameByTeamId(loadedGame.awayTeamId.toInt()) }.getOrDefault(
                        "Visitante"
                    )
                _uiState.update {
                    it.copy(
                        game = loadedGame,
                        homeTeamName = homeName,
                        awayTeamName = awayName,
                        currentPeriod = loadedGame.currentPeriod.coerceAtLeast(1),
                        clockSecondsRemaining = loadedGame.clockSecondsRemaining
                            ?: DEFAULT_PERIOD_SECONDS,
                        clockStartedAtEpochMs = loadedGame.clockStartedAtEpochMs,
                        isClockRunning = loadedGame.clockStartedAtEpochMs != null,
                        isLoading = false,
                        error = null
                    )
                }
                observeRosters(loadedGame)
                observeFouls(loadedGame)
                if (loadedGame.clockStartedAtEpochMs != null) startClockTicker()
            }
        }
        scoreJob = viewModelScope.launch {
            gameEventUseCase.observeScore(gameId.toLong()).collect { score ->
                _uiState.update {
                    it.copy(
                        scoreHomeTeam = score.homeTeamScore, scoreAwayTeam = score.awayTeamScore
                    )
                }
            }
        }
        statsJob = viewModelScope.launch {
            gameEventUseCase.observePlayerStats(gameId.toLong()).collect { stats ->
                _uiState.update { it.copy(playerStats = stats) }
            }
        }
        eventsJob = viewModelScope.launch {
            gameEventUseCase.observeEvents(gameId.toLong()).collect { events ->
                val game = _uiState.value.game ?: return@collect
                _uiState.update {
                    it.copy(
                        homeTimeouts = events.count { event -> !event.isCancelled && event.eventType == "TIMEOUT" && event.teamId == game.homeTeamId },
                        awayTimeouts = events.count { event -> !event.isCancelled && event.eventType == "TIMEOUT" && event.teamId == game.awayTeamId })
                }
            }
        }
    }

    fun recordScore(isHomeTeam: Boolean, points: Int, rosterId: Long? = null) {
        require(points in 1..3) { "Los puntos deben estar entre 1 y 3" }
        recordEvent(if (points == 1) "FREE_THROW" else "SCORE", isHomeTeam, points, rosterId)
    }

    fun updatePeriod(period: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(currentPeriod = period)
            }
            gameUseCase.updateGame(
                _uiState.value.game!!.copy(currentPeriod = period)
            )
        }
    }

    fun recordFoul(isHomeTeam: Boolean, rosterId: Long? = null, foulType: String? = null) {
        val game = _uiState.value.game ?: return setError("No se ha cargado el partido")
        val teamId = if (isHomeTeam) game.homeTeamId else game.awayTeamId
        viewModelScope.launch {
            runCatching {
                gameEventUseCase.recordFoul(
                    newEvent(
                        "FOUL", teamId, rosterId, 0, foulType

                    )
                )
            }.onFailure { setError(it.message ?: "No se pudo registrar la falta") }
        }
    }

    fun selectPlayerFor(isHomeTeam: Boolean, action: PendingAction) {
        val roster =
            if (isHomeTeam) _uiState.value.rostersHomeTeam else _uiState.value.rostersAwayTeam
        if (roster.isEmpty()) return setError("No hay jugadores registrados para este equipo")
        _uiState.update {
            it.copy(
                dialog = true,
                dialogRoster = roster,
                pendingAction = action.copy(isHomeTeam = isHomeTeam)
            )
        }
    }

    fun onRosterSelected(roster: GameRosterModel) {
        val action = _uiState.value.pendingAction ?: return
        onChangeDialog(false)
        when (action.type) {
            PendingActionType.SCORE -> recordScore(action.isHomeTeam, action.points, roster.id)
            PendingActionType.FOUL -> recordFoul(action.isHomeTeam, roster.id)
        }
    }

    fun onChangeDialog(dialog: Boolean) {
        _uiState.update {
            it.copy(
                dialog = dialog,
                dialogRoster = if (dialog) it.dialogRoster else emptyList(),
                pendingAction = if (dialog) it.pendingAction else null
            )
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }

    fun startClock() {
        val game = _uiState.value.game ?: return setError("No se ha cargado el partido")
        if (_uiState.value.isClockRunning) return
        val seconds = _uiState.value.clockSecondsRemaining
        if (seconds <= 0) return setError("El reloj está en cero; inicia el siguiente cuarto")

        val startedAt = System.currentTimeMillis()
        _uiState.update {
            it.copy(
                isClockRunning = true, clockStartedAtEpochMs = startedAt, error = null
            )
        }
        startClockTicker()
        persistClock(game.id, _uiState.value.currentPeriod, seconds, startedAt)
    }

    fun pauseClock(persist: Boolean = true) {
        val game = _uiState.value.game ?: return
        val seconds = _uiState.value.clockSecondsRemaining
        val period = _uiState.value.currentPeriod

        clockJob?.cancel()
        clockJob = null

        _uiState.update {
            it.copy(
                isClockRunning = false,
                clockStartedAtEpochMs = null
            )
        }

        if (persist) {
            persistClock(
                game.id,
                period,
                seconds,
                null
            )
        }
        Log.d("StartGameVM", "🔥🔥🔥 pauseClock")
    }

    fun changePeriod(delta: Int) {
        val game = _uiState.value.game ?: return
        val period = (_uiState.value.currentPeriod + delta).coerceAtLeast(1)

        viewModelScope.launch {
            if (gameUseCase.checkPeriod(period, _uiState.value.isClockRunning, _uiState.value.clockSecondsRemaining)) {
                clockJob?.cancel()
                clockJob = null
                _uiState.update {
                    it.copy(
                        currentPeriod = period,
                        clockSecondsRemaining = DEFAULT_PERIOD_SECONDS,
                        clockStartedAtEpochMs = null,
                        isClockRunning = false
                    )
                }
                persistClock(game.id, period, DEFAULT_PERIOD_SECONDS, null)
                gameUseCase.updateGame(game.copy(currentPeriod = period))
            }
        }
    }

    fun requestTimeout() {
        if (_uiState.value.game == null) return setError("No se ha cargado el partido")
        pauseClock()
        _uiState.update { it.copy(showTimeoutDialog = true) }
    }

    fun registerTimeout(isHomeTeam: Boolean) {
        val game = _uiState.value.game ?: return setError("No se ha cargado el partido")
        val teamId = if (isHomeTeam) game.homeTeamId else game.awayTeamId
        _uiState.update { it.copy(showTimeoutDialog = false) }
        viewModelScope.launch {
            runCatching {
                gameEventUseCase.record(
                    newEvent(
                        "TIMEOUT",
                        teamId,
                        null,
                        0
                    )
                )
            }.onFailure { setError(it.message ?: "No se pudo registrar el tiempo fuera") }
        }
    }

    fun dismissTimeoutDialog() = _uiState.update { it.copy(showTimeoutDialog = false) }

    fun showTeamStats(isHomeTeam: Boolean) {
        val game = _uiState.value.game ?: return setError("No se ha cargado el partido")
        val teamId = if (isHomeTeam) game.homeTeamId else game.awayTeamId
        val teamName = if (isHomeTeam) _uiState.value.homeTeamName else _uiState.value.awayTeamName
        _uiState.update {
            it.copy(
                teamStatsDialog = TeamStatsDialogState(
                    teamId, teamName, isHomeTeam
                )
            )
        }
    }

    fun dismissTeamStats() = _uiState.update { it.copy(teamStatsDialog = null) }

    private fun recordEvent(eventType: String, isHomeTeam: Boolean, points: Int, rosterId: Long?) {
        val game = _uiState.value.game ?: return setError("No se ha cargado el partido")
        val teamId = if (isHomeTeam) game.homeTeamId else game.awayTeamId
        viewModelScope.launch {
            runCatching {
                gameEventUseCase.record(
                    newEvent(
                        eventType,
                        teamId,
                        rosterId,
                        points
                    )
                )
            }.onFailure { setError(it.message ?: "No se pudo registrar el evento") }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun newEvent(
        type: String, teamId: Long, rosterId: Long?, points: Int, foulType: String? = null
    ): GameEventEntity {
        val game = requireNotNull(_uiState.value.game)
        return GameEventEntity(
            gameId = game.id,
            sequenceNumber = 0,
            periodNumber = _uiState.value.currentPeriod,
            clockSecondsRemaining = _uiState.value.clockSecondsRemaining,
            eventType = type,
            teamId = teamId,
            rosterId = rosterId,
            points = points,
            foulType = foulType,
            createdAt = Instant.now().toString()
        )
    }

    private fun observeRosters(game: GameModel) {
        rosterJob?.cancel()
        rosterJob = viewModelScope.launch {
            gameRosterUseCase.getAllOfGame(game.id).collect { rosters ->
                _uiState.update {
                    it.copy(
                        rostersHomeTeam = rosters.filter { roster -> roster.teamId == game.homeTeamId },
                        rostersAwayTeam = rosters.filter { roster -> roster.teamId == game.awayTeamId })
                }
            }
        }
    }

    private fun observeFouls(game: GameModel) {
        foulsJob?.cancel()
        foulsJob = viewModelScope.launch {
            teamPeriodFoulUseCase.observeForGame(game.id)
                .collect { fouls -> updatePeriodFouls(game, fouls) }
        }
    }

    private fun updatePeriodFouls(game: GameModel, fouls: List<TeamPeriodFoulModel>) {
        _uiState.update {
            it.copy(
                homeTeamFouls = fouls.firstOrNull { foul -> foul.teamId == game.homeTeamId && foul.periodNumber == game.currentPeriod }?.foulCount
                    ?: 0,
                awayTeamFouls = fouls.firstOrNull { foul -> foul.teamId == game.awayTeamId && foul.periodNumber == game.currentPeriod }?.foulCount
                    ?: 0
            )
        }
    }

    private fun setError(message: String) =
        _uiState.update { it.copy(error = message, isLoading = false) }

    private fun persistClock(gameId: Long, period: Int, seconds: Int, startedAt: Long?) {
        viewModelScope.launch {
            runCatching {
                gameUseCase.updateGameClock(
                    gameId,
                    period,
                    seconds,
                    startedAt
                )
            }.onFailure { setError(it.message ?: "No se pudo guardar el reloj"); Log.d("StartGameVM", it.message ?: "No se pudo guardar el reloj") }
        }
    }

    private fun startClockTicker() {
        if (clockJob?.isActive == true) return
        clockJob = viewModelScope.launch {
            while (_uiState.value.isClockRunning) {
                val startedAt = _uiState.value.clockStartedAtEpochMs ?: break
                val elapsedSeconds = ((System.currentTimeMillis() - startedAt) / 1_000).toInt()
                val seconds = (_uiState.value.game?.clockSecondsRemaining
                    ?: _uiState.value.clockSecondsRemaining).minus(elapsedSeconds).coerceAtLeast(0)
                _uiState.update { it.copy(clockSecondsRemaining = seconds) }
                if (seconds == 0) {
                    pauseClock()
                    break
                }
                delay(250)
            }
        }
    }

    private fun cancelObservers() {
        gameJob?.cancel(); rosterJob?.cancel(); scoreJob?.cancel(); statsJob?.cancel(); foulsJob?.cancel(); eventsJob?.cancel(); clockJob?.cancel()
    }


    /***
     *
     * Pausa del Relog al momento de salir de la pantalla
     *
     */
    override fun onCleared() {
        cancelObservers()
        super.onCleared()
    }

    private fun onChangeIsChangePeriodMin(isChangePeriod: Boolean) {
        _uiState.update { it.copy(isChangePeriodMin = isChangePeriod) }
    }

    private fun onChangeIsChangePeriodMax(isChangePeriod: Boolean) {
        _uiState.update { it.copy(isChangePeriodMax = isChangePeriod) }
    }
}

data class StartGameUiState(
    val idGame: Int = 0,
    val game: GameModel? = null,
    val homeTeamName: String = "Local",
    val awayTeamName: String = "Visitante",
    val rostersHomeTeam: List<GameRosterModel> = emptyList(),
    val rostersAwayTeam: List<GameRosterModel> = emptyList(),
    val dialogRoster: List<GameRosterModel> = emptyList(),
    val playerStats: List<PlayerGameStatsModel> = emptyList(),
    val scoreHomeTeam: Int = 0,
    val scoreAwayTeam: Int = 0,
    val currentPeriod: Int = 1,
    val clockSecondsRemaining: Int = DEFAULT_PERIOD_SECONDS,
    val clockStartedAtEpochMs: Long? = null,
    val isClockRunning: Boolean = false,
    val homeTimeouts: Int = 0,
    val awayTimeouts: Int = 0,
    val homeTeamFouls: Int = 0,
    val awayTeamFouls: Int = 0,
    val dialog: Boolean = false,
    val showTimeoutDialog: Boolean = false,
    val teamStatsDialog: TeamStatsDialogState? = null,
    val pendingAction: PendingAction? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isChangePeriodMin: Boolean = false,
    val isChangePeriodMax: Boolean = false
)

data class PendingAction(
    val type: PendingActionType, val isHomeTeam: Boolean = true, val points: Int = 0
)

data class TeamStatsDialogState(val teamId: Long, val teamName: String, val isHomeTeam: Boolean)
enum class PendingActionType { SCORE, FOUL }

private const val DEFAULT_PERIOD_SECONDS = 10 * 60
