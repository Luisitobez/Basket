package luisitobez.jjvh.basket.ui.Screen.StartGame

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import luisitobez.jjvh.basket.ui.theme.AppBackColor
import luisitobez.jjvh.basket.ui.theme.AppBlackTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextBlue
import luisitobez.jjvh.basket.ui.theme.AppTextPrimary
import luisitobez.jjvh.basket.ui.theme.AppTextRed
import luisitobez.jjvh.basket.ui.theme.AppTextSecondary
import luisitobez.jjvh.basket.ui.theme.AppTintIcon
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange

@Composable
fun StartGameScreen(
    viewModel: StartGameViewModel = hiltViewModel(),
    modifier: Modifier,
    id: Int
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.loadGame(id)
    }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = AppTextRed,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = AppBorderShape.default(),
                colors = CardDefaults.cardColors(
                    containerColor = AppBlackTransparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    //verticalArrangement = Arrangement.spacedBy(8.dp)
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.homeTeamName,
                                color = AppTextBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                maxLines = 1
                            )
                            Icon(
                                imageVector = Icons.Default.SportsBasketball,
                                contentDescription = null,
                                tint = AppTextBlue,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(top = 32.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(

                            ) {
                                Text(
                                    text = uiState.scoreHomeTeam.toString(),
                                    color = AppTextBlue,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = " - ",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = uiState.scoreAwayTeam.toString(),
                                    color = AppTextRed,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.awayTeamName,
                                color = AppTextRed,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                            Icon(
                                imageVector = Icons.Default.SportsBasketball,
                                contentDescription = null,
                                tint = AppTextRed,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                    Text(
                        text = "CUARTO",
                        color = AppTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.changePeriod(-1) },
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = PrimaryOrange,
                                    shape = AppBorderShape.default()
                                ),
                            shape = AppBorderShape.default(),
                            colors = buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = PrimaryOrange
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = null,
                                tint = PrimaryOrange,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = uiState.currentPeriod.toString(),
                            color = AppTextPrimary,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 24.dp)
                        )


                        Button(
                            onClick = { viewModel.changePeriod(1) },
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = PrimaryOrange,
                                    shape = AppBorderShape.default()
                                ),
                            shape = AppBorderShape.default(),
                            colors = buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = PrimaryOrange
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = PrimaryOrange,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )

                    Text(
                        text = "TIEMPO RESTANTE",
                        color = AppTextSecondary,
                        fontSize = 12.sp,
                    )

                    Text(
                        text = formatClock(uiState.clockSecondsRemaining),
                        color = AppTextPrimary,
                        fontSize = 24.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = viewModel::startClock,
                            enabled = !uiState.isClockRunning && uiState.clockSecondsRemaining > 0,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = AppTintIcon,
                                    shape = AppBorderShape.default()
                                )
                                .weight(1f),
                            shape = AppBorderShape.default(),
                            colors = buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = PrimaryOrange
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = AppTintIcon,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Button(
                            onClick = viewModel::pauseClock,
                            enabled = uiState.isClockRunning,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = PrimaryOrange,
                                    shape = AppBorderShape.default()
                                )
                                .weight(1f),
                            shape = AppBorderShape.default(),
                            colors = buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = PrimaryOrange
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = null,
                                tint = PrimaryOrange,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Button(
                            onClick = viewModel::requestTimeout,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = AppBackColor,
                                    shape = AppBorderShape.default()
                                )
                                .weight(1f),
                            shape = AppBorderShape.default(),
                            colors = buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = PrimaryOrange
                            )
                        ) {
                            Text(
                                text = "TF",
                                color = AppTextRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = AppBorderShape.default(),
                colors = CardDefaults.cardColors(
                    containerColor = AppBlackTransparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "MARCADOR",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickScoreButton(
                            text = "+1",
                            color = AppTextBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    true,
                                    PendingAction(PendingActionType.SCORE, points = 1)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "+2",
                            color = AppTextBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    true,
                                    PendingAction(PendingActionType.SCORE, points = 2)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "+3",
                            color = AppTextBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    true,
                                    PendingAction(PendingActionType.SCORE, points = 3)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "+1",
                            color = AppTextRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    false,
                                    PendingAction(PendingActionType.SCORE, points = 1)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "+2",
                            color = AppTextRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    false,
                                    PendingAction(PendingActionType.SCORE, points = 2)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "+3",
                            color = AppTextRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    false,
                                    PendingAction(PendingActionType.SCORE, points = 3)
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickScoreButton(
                            text = "TIRO LIBRE",
                            color = AppTextBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    true,
                                    PendingAction(PendingActionType.SCORE, points = 1)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "TIRO LIBRE",
                            color = AppTextRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    false,
                                    PendingAction(PendingActionType.SCORE, points = 1)
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickScoreButton(
                            text = "FALTA",
                            color = AppTextBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    true,
                                    PendingAction(PendingActionType.FOUL)
                                )
                            }
                        )

                        QuickScoreButton(
                            text = "FALTA",
                            color = AppTextRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectPlayerFor(
                                    false,
                                    PendingAction(PendingActionType.FOUL)
                                )
                            }
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            TeamScoreCard(
                teamName = uiState.homeTeamName,
                score = uiState.scoreHomeTeam,
                teamColor = AppTextBlue,
                teamFouls = uiState.homeTeamFouls,
                timeouts = uiState.homeTimeouts,
                players = uiState.playerStats.filter { player -> player.teamId == uiState.game?.homeTeamId },
                onClick = { viewModel.showTeamStats(isHomeTeam = true) }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            TeamScoreCard(
                teamName = uiState.awayTeamName,
                score = uiState.scoreAwayTeam,
                teamColor = AppTextRed,
                teamFouls = uiState.awayTeamFouls,
                timeouts = uiState.awayTimeouts,
                players = uiState.playerStats.filter { player -> player.teamId == uiState.game?.awayTeamId },
                onClick = { viewModel.showTeamStats(isHomeTeam = false) }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }
    }

    if (
        uiState.dialog
    ) {
        DialogRosters(
            listOfRosters = uiState.dialogRoster,
            onDismiss = { viewModel.onChangeDialog(false) },
            onRosterSelected = viewModel::onRosterSelected
        )
    }

    if (uiState.showTimeoutDialog) {
        DialogTimeOut(
            homeTeamName = uiState.homeTeamName,
            awayTeamName = uiState.awayTeamName,
            onDismiss = {
                viewModel.dismissTimeoutDialog()
                viewModel.startClock()
                        },
            onHomeTeamSelected = {
                viewModel.registerTimeout(true)
            },
            onAwayTeamSelected = {
                viewModel.registerTimeout(false)
            }
        )
    }

    uiState.teamStatsDialog?.let { dialog ->
        DialogTeamStats(
            teamName = dialog.teamName,
            teamColor = if (dialog.isHomeTeam) AppTextBlue else AppTextRed,
            players = uiState.playerStats.filter { player -> player.teamId == dialog.teamId },
            onDismiss = viewModel::dismissTeamStats
        )
    }
}

private fun formatClock(seconds: Int?): String {
    val safeSeconds = (seconds ?: 0).coerceAtLeast(0)
    return "%02d:%02d".format(safeSeconds / 60, safeSeconds % 60)
}
