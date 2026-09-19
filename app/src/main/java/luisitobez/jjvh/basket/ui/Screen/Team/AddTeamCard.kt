package luisitobez.jjvh.basket.ui.Screen.Team

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppCardColors
import luisitobez.jjvh.basket.ui.theme.AppModifierCard

@Composable
fun AddTeamCard(
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = AppModifierCard.default(),
        shape = AppBorderShape.default(),
        colors = AppCardColors.default()
    ) {
        Text(
            text = "Agregar equipo",
            modifier = Modifier.padding(16.dp)
        )
    }
}