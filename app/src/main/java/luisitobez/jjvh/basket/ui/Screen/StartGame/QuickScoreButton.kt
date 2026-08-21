package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import luisitobez.jjvh.basket.ui.theme.AppBorderShape

@Composable
fun QuickScoreButton(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = AppBorderShape.default(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color
        ),
        border = BorderStroke(
            width = 1.dp,
            color = color
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
    }
}