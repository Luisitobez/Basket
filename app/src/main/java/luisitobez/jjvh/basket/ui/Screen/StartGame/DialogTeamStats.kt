package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextPrimary
import luisitobez.jjvh.basket.ui.theme.AppTextRed
import luisitobez.jjvh.basket.ui.theme.AppTextSecondary
import luisitobez.jjvh.basket.ui.theme.AppTintIcon

@Composable
fun DialogTeamStats(
    teamName: String,
    teamColor: Color,
    players: List<PlayerGameStatsModel>,
    onDismiss: () -> Unit
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
                .fillMaxWidth(0.92f)
                .border(
                    width = 1.dp,
                    color = teamColor,
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
                    .padding(20.dp)
            ) {

                Text(
                    text = teamName,
                    color = teamColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "ESTADÍSTICAS DE JUGADORES",
                    color = AppTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Encabezados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "JUGADOR",
                        color = AppTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "PTS",
                        color = AppTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(45.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "F",
                        color = AppTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(45.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                HorizontalDivider(
                    color = Color(0xFF353535)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    items(
                        items = players,
                        key = { it.rosterId }
                    ) { player ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = AppBorderShape.default(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 12.dp,
                                        vertical = 10.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = player.playerName,
                                        color = AppTextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "#${player.jerseyNumber}",
                                        color = AppTextSecondary,
                                        fontSize = 11.sp
                                    )
                                }

                                Text(
                                    text = player.points.toString(),
                                    color = teamColor,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(45.dp),
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = player.fouls.toString(),
                                    color = if (player.fouls >= 5) {
                                        AppTextRed
                                    } else {
                                        AppTextPrimary
                                    },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(45.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
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
                        text = "CERRAR",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
