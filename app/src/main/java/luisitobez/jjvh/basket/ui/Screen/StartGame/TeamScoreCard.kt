package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape

@Composable
fun TeamScoreCard(
    teamName: String,
    score: Int,
    teamColor: Color,
    teamFouls: Int,
    timeouts: Int,
    players: List<PlayerGameStatsModel>,
    maxTeamFouls: Int = 5,
    maxTimeouts: Int = 7,
    onClick: () -> Unit
) {
    // Los 3 jugadores con más faltas
    val topFoulPlayers = players
        .sortedByDescending { it.fouls }
        .take(3)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = AppBorderShape.default(),
        colors = CardDefaults.cardColors(
            containerColor = AppBlackTransparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF353535)
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
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
                    text = score.toString(),
                    color = teamColor,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // FALTAS DE EQUIPO + TOP 3 JUGADORES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {

                    // Parte izquierda
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "FALTAS DE EQUIPO",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        TeamIndicators(
                            current = teamFouls,
                            max = maxTeamFouls,
                            color = teamColor
                        )
                        // TIEMPOS FUERA
                        Text(
                            text = "TIEMPOS FUERA",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        TeamIndicators(
                            current = timeouts,
                            max = maxTimeouts,
                            color = teamColor
                        )
                    }


                    // Parte derecha
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "FALTAS",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        topFoulPlayers.forEach { player ->

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "#${player.jerseyNumber}",
                                    color = teamColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = player.playerName,
                                    color = Color.LightGray,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )

                                Text(
                                    text = "${player.fouls}",
                                    color = if (player.fouls >= 5) {
                                        Color.Red
                                    } else {
                                        Color.White
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            Icon(
                imageVector = Icons.Default.SportsBasketball,
                contentDescription = null,
                tint = Color(0x332F2F2F),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}