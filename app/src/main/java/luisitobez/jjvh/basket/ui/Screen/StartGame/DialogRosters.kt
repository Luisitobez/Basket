package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.ui.theme.AppBackColor
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextBlue
import luisitobez.jjvh.basket.ui.theme.AppTextPrimary
import luisitobez.jjvh.basket.ui.theme.AppTextSecondary
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange

@Composable
fun DialogRosters(
    listOfRosters: List<GameRosterModel>,
    onDismiss: () -> Unit,
    onRosterSelected: (GameRosterModel) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
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
                    .padding(16.dp)
            ) {

                // Título
                Text(
                    text = "JUGADORES",
                    color = AppTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Selecciona un jugador",
                    color = AppTextSecondary,
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Línea decorativa
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(PrimaryOrange)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Lista de jugadores
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(
                        items = listOfRosters
                    ) { roster ->

                        Card(
                            onClick = {
                                onRosterSelected(roster)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = AppBorderShape.default(),
                            colors = CardDefaults.cardColors(
                                containerColor = AppBackColor
                            )
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Número de camiseta
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .border(
                                            width = 1.dp,
                                            color = AppTextBlue,
                                            shape = AppBorderShape.default()
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = roster.jerseyNumber?.toString() ?: "-",
                                        color = AppTextBlue,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.size(12.dp)
                                )

                                // Nombre
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = roster.playerName ?: "Jugador sin nombre",
                                        color = AppTextPrimary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Jugador",
                                        color = AppTextSecondary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Botón cancelar
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = PrimaryOrange,
                            shape = AppBorderShape.default()
                        ),
                    shape = AppBorderShape.default(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = PrimaryOrange
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