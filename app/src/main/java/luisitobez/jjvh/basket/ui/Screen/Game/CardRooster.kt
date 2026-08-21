package luisitobez.jjvh.basket.ui.Screen.Game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorder
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppBlueTransparent
import luisitobez.jjvh.basket.ui.theme.AppGreyTransparent
import luisitobez.jjvh.basket.ui.theme.AppTextBlue

@Composable
fun CardRooster(
    Delete: (Long) -> Unit,
    PlayerName: String,
    JerseyNumber: Int,
    color: Color,
    fondo: Color

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        shape = AppBorderShape.any(),
        colors = CardDefaults.cardColors(
            containerColor = AppBlackTransparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = color,
                        shape = CircleShape
                    )
                    .background(fondo),
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    text = JerseyNumber.toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(7f),
            ) {
                Text(
                    text = PlayerName,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                IconButton(
                    onClick = {
                        Delete(JerseyNumber.toLong())
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Delete",
                        tint = Color.LightGray,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}