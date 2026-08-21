package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TeamIndicators(
    current: Int,
    max: Int,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        repeat(max) { index ->

            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(
                        color = if (index < current) {
                            color
                        } else {
                            Color(0xFF777777)
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}