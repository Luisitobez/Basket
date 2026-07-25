package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

@Composable
fun PrincipalScreen(
    viewModel: PrincipalViewModel = PrincipalViewModel(),
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Pantalla Principal")
        Button(onClick = onStartGame) {
            Text("Iniciar Juego")
        }
    }
}