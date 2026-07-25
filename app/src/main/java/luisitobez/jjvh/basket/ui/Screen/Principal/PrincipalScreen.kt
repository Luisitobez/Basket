package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PrincipalScreen(
    viewModel: PrincipalViewModel = hiltViewModel(),
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        Text("Pantalla Principal")
        Button(onClick = onStartGame) {
            Text("Iniciar Juego")
        }
    }
}