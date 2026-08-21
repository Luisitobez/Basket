package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextBlue
import luisitobez.jjvh.basket.ui.theme.AppTextPrimary
import luisitobez.jjvh.basket.ui.theme.AppTextRed
import luisitobez.jjvh.basket.ui.theme.AppTextSecondary
import luisitobez.jjvh.basket.ui.theme.AppTintIcon
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange

@Composable
fun DialogTimeOut(
    homeTeamName: String,
    awayTeamName: String,
    onDismiss: () -> Unit,
    onHomeTeamSelected: () -> Unit,
    onAwayTeamSelected: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .border(
                    width = 1.dp,
                    color = PrimaryOrange,
                    shape = AppBorderShape.default()
                ),
            shape = AppBorderShape.default(),
            colors = CardDefaults.cardColors(
                containerColor = AppBlackTransparent
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "TIEMPO FUERA",
                    color = AppTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "¿Qué equipo solicitó el tiempo fuera?",
                    color = AppTextSecondary,
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    onClick = onHomeTeamSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = AppTextBlue,
                            shape = AppBorderShape.default()
                        ),
                    shape = AppBorderShape.default(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = AppTextBlue
                    )
                ) {
                    Text(
                        text = homeTeamName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Button(
                    onClick = onAwayTeamSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = AppTextRed,
                            shape = AppBorderShape.default()
                        ),
                    shape = AppBorderShape.default(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = AppTextRed
                    )
                ) {
                    Text(
                        text = awayTeamName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = AppTintIcon,
                            shape = AppBorderShape.default()
                        ),
                    shape = AppBorderShape.default(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = AppTextSecondary
                    )
                ) {
                    Text(
                        text = "CANCELAR",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}