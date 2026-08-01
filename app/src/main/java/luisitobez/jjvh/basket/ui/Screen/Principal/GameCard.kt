package luisitobez.jjvh.basket.ui.Screen.Principal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppCardColors
import luisitobez.jjvh.basket.ui.theme.AppModifierCard
import luisitobez.jjvh.basket.ui.theme.ContainerColor
import luisitobez.jjvh.basket.ui.theme.ShapeCardColor

@Composable
fun GameCard(
    onClick: () -> Unit,
    homeTeam: String,
    awayTeam: String
) {
    Card(
        onClick = onClick,
        modifier = AppModifierCard.default(),
        shape = AppBorderShape.default(),
        colors = AppCardColors.default()
    ) {
        Text(
            text = "$homeTeam vs $awayTeam",
            modifier = Modifier.padding(16.dp)
        )
    }
}