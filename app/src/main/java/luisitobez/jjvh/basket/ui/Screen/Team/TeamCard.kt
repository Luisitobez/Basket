package luisitobez.jjvh.basket.ui.Screen.Team

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppCardColors
import luisitobez.jjvh.basket.ui.theme.AppModifierCard

@Composable
fun TeamCard(
    teamName: String,
    teamShortName: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = AppModifierCard.default(),
        shape = AppBorderShape.default(),
        colors = AppCardColors.default()
    ) {
        Text(
            text = teamName,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Text(
            text = teamShortName,
            modifier = Modifier.padding(start = 32.dp, bottom = 16.dp),
            fontSize = 10.sp
        )

    }

}