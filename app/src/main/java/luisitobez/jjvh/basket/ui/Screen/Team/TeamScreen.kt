package luisitobez.jjvh.basket.ui.Screen.Team

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TeamScreen(
    viewModel: TeamViewModel = hiltViewModel(),
    onAddTeamClick: () -> Unit,
    onTeamClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
    ) {
        item {
            AddTeamCard(
                onClick = {
                    onAddTeamClick()
                }
            )
        }
        items(items = uiState.teams) { team ->
            TeamCard(
                teamName = team.name,
                teamShortName = team.shortName ?: "Null",
                onClick = { onTeamClick(team.id.toInt()) })
        }
    }
}