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

@Composable
fun GameCard(
    onClick: () -> Unit,
    homeTeam: String,
    awayTeam: String
) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(8.dp).fillMaxWidth().border(
            width = 2.dp,
            color = Color(0xFFF9A825),
            shape = RoundedCornerShape(8.dp)
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0606DA),
        )
    ) {
        Text(
            text = "$homeTeam vs $awayTeam",
            modifier = Modifier.padding(16.dp)
        )
    }
}