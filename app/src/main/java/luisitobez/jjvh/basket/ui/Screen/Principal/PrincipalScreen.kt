package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
    viewModel: PrincipalViewModel = hiltViewModel(),
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToGame: (Long) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column {
                AddGameCard(
                    onClick = onStartGame
                )
            }
        }
        items(
            items = uiState.games
        ) { game ->
            GameCard(
                onClick = { onNavigateToGame(game.id) },
                homeTeam = uiState.teams.firstOrNull { it.id == game.homeTeamId }?.name ?: "Equipo Local",
                awayTeam = uiState.teams.firstOrNull { it.id == game.awayTeamId }?.name ?: "Equipo Visitante",
                fecha = game.gameDate,
                lugar = game.venue ?: "Canchas"
            )
        }
    }
}