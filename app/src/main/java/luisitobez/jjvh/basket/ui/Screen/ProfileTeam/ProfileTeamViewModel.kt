package luisitobez.jjvh.basket.ui.Screen.ProfileTeam

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
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileTeamViewModel @Inject constructor(
    val teamUseCase: TeamUseCase,
    val gameUseCase: GameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileTeamUiState())
    val uiState: StateFlow<ProfileTeamUiState> = _uiState.asStateFlow()


    fun getTeamById(id: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val team = teamUseCase.getTeamById(id)
                _uiState.update {
                    it.copy(
                        id = id,
                        name = team.name,
                        shortName = team.shortName ?: "Sin nombre corto",
                        logoUri = team.logoUri ?: "",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
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

    fun onChangeLogoUri(logoUri: String) {
        _uiState.value = _uiState.value.copy(
            logoUri = logoUri
        )
    }

    fun onClickEdit() {
        _uiState.update {
            it.copy(
                isEditing = !it.isEditing
            )
        }
    }

    fun onClickDelete() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val deleted = teamUseCase.deleteTeam(
                    id = uiState.value.id,
                    name = uiState.value.name,
                    shortName = uiState.value.shortName,
                    uri = uiState.value.logoUri
                )

                if (deleted) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            showDialog = false,
                            deleted = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showDialog = false,
                            error = "No se pudo eliminar el equipo."
                        )
                    }
                }
            } catch (e: Exception) {
                val message = "${e.message.orEmpty()} ${e.cause?.message.orEmpty()}"
                val userMessage = if (message.contains("FOREIGN KEY", ignoreCase = true)) {
                    "No se puede eliminar el equipo porque tiene partidos o registros asociados."
                } else {
                    e.message ?: "Error desconocido"
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        showDialog = false,
                        error = userMessage
                    )
                }
            }
        }
    }

    fun onChangeShowDialog(showDialog: Boolean) {
        _uiState.update {
            it.copy(
                showDialog = showDialog
            )
        }
    }


    fun onClickSave() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                teamUseCase.updateTeam(
                    id = uiState.value.id,
                    name = uiState.value.name,
                    shortName = uiState.value.shortName,
                    uri = uiState.value.logoUri
                )
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }


    fun getGamesByTeamId(teamId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                gameUseCase.getGamesByTeamId(teamId).collect { games ->

                    _uiState.update {
                        it.copy(
                            listGame = games
                        )
                    }

                    games.forEach { game ->
                        getNameTeamById(game.homeTeamId.toInt())
                        getNameTeamById(game.awayTeamId.toInt())
                    }
                }
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
            for (game in uiState.value.listGame) {
                getNameTeamById(game.homeTeamId.toInt())
                getNameTeamById(game.awayTeamId.toInt())
            }
        }
    }


    fun getNameTeamById(id: Int) {
        if (!uiState.value.listTeams.containsKey(id)) {
            if (id == uiState.value.id) {
                _uiState.update {
                    it.copy(
                        listTeams = it.listTeams + mapOf(id to uiState.value.name)
                    )
                }
            } else {
                viewModelScope.launch {
                    try {
                        val team = teamUseCase.getTeamById(id)
                        _uiState.update {
                            it.copy(
                                listTeams = it.listTeams + mapOf(id to team.name)
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Error desconocido"
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteGame(game: GameModel) {
        viewModelScope.launch {
            gameUseCase.deleteGame(game)

        }
    }

}

data class ProfileTeamUiState(
    val id: Int = 0,
    val name: String = "",
    val shortName: String = "",
    val logoUri: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEditing: Boolean = false,
    val showDialog: Boolean = false,
    val deleted: Boolean = false,
    val listGame: List<GameModel> = emptyList(),
    val listTeams: Map<Int, String> = emptyMap()
)