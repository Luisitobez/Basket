package luisitobez.jjvh.basket.ui.Screen.Game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import luisitobez.jjvh.basket.ui.Screen.AddGame.StatusSelector
import luisitobez.jjvh.basket.ui.Screen.AddGame.TeamSelector
import luisitobez.jjvh.basket.ui.theme.AppBlueTransparent
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppButtonColors
import luisitobez.jjvh.basket.ui.theme.AppModifierButton
import luisitobez.jjvh.basket.ui.theme.AppRedTransparent
import luisitobez.jjvh.basket.ui.theme.AppShapeButton
import luisitobez.jjvh.basket.ui.theme.AppTextBlue
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors
import luisitobez.jjvh.basket.ui.theme.AppTextRed
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange
import java.time.Instant
import java.time.ZoneId

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    modifier: Modifier,
    id: Int,
    onNavigateToStartGame: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val datePickerState = rememberDatePickerState()


    LaunchedEffect(id) {
        viewModel.getGameById(id)
        viewModel.onGetListOfTeams()
        viewModel.onChangeTeams()
    }

    LazyColumn(modifier = modifier) {


        item {
            Text(
                text = "EQUIPO LOCAL",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .fillMaxWidth()
            ) {
                TeamSelector(
                    selectedTeam = uiState.listOfTeams
                        .find { it.id.toInt() == uiState.homeTeamId }
                        ?.name
                        ?: "Seleccionar equipo",

                    teams = uiState.listOfTeams,

                    excludedTeamId = uiState.awayTeamId,

                    expanded = uiState.expanded1,

                    onExpandedChange = { expanded ->
                        viewModel.onChangeExpanded1(expanded)
                    },

                    onTeamSelected = { teamId ->
                        viewModel.onChangeHomeTeamId(teamId)
                    },
                )
            }

            Text(
                text = "EQUIPO VISITANTE",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .fillMaxWidth()
            ) {
                TeamSelector(
                    selectedTeam = uiState.listOfTeams
                        .find { it.id.toInt() == uiState.awayTeamId }
                        ?.name
                        ?: "Seleccionar equipo",

                    teams = uiState.listOfTeams,

                    excludedTeamId = uiState.homeTeamId,

                    expanded = uiState.expanded2,

                    onExpandedChange = { expanded ->
                        viewModel.onChangeExpanded2(expanded)
                    },

                    onTeamSelected = { teamId ->
                        viewModel.onChangeAwayTeamId(teamId)
                    }
                )
            }

            Text(
                text = "LUGAR",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = uiState.venue,
                onValueChange = {
                    viewModel.onChangeVenue(it)
                },
                label = {
                    Text("Lugar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                colors = AppTextFieldColors.default(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PrimaryOrange
                    )
                },
                shape = AppBorderShape.default(),
            )

            Text(
                text = "FECHA",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onChangeShowDatePicker(true)
                    }
            ) {
                OutlinedTextField(
                    value = uiState.datePickerState ?: "",
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    label = {
                        Text("Fecha")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha",
                            tint = PrimaryOrange
                        )
                    },
                    colors = AppTextFieldColors.default(),
                    shape = AppBorderShape.default()
                )
            }

            Text(
                text = "ESTADO",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .fillMaxWidth()
            ) {
                StatusSelector(
                    selectedStatus = uiState.status,

                    statuses = uiState.mapStatus,

                    expanded = uiState.expandedStatus,

                    onExpandedChange = { expanded ->
                        viewModel.onChangeExpandedStatus(expanded)
                    },

                    onStatusSelected = { status ->
                        viewModel.onChangeStatus(status)
                    }
                )
            }

            Text(
                text = "NOTAS",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = {
                    viewModel.onChangeNotes(it)
                },
                label = {
                    Text("Notas")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .height(120.dp),
                colors = AppTextFieldColors.default(),
                maxLines = 4,
                minLines = 4,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = PrimaryOrange
                    )
                },
                shape = AppBorderShape.default()
            )

            Text(
                text = "JUGADORES",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )


            TabRow(
                selectedTabIndex = if (uiState.team) 0 else 1,
                modifier = Modifier.padding(horizontal = 16.dp),
                containerColor = Color.Transparent,
                contentColor = PrimaryOrange
            ) {
                Tab(
                    selected = uiState.team,
                    onClick = { viewModel.onChangeTeam(true) },
                    text = {
                        Text(
                            uiState.listOfTeams
                                .firstOrNull { it.id.toInt() == uiState.homeTeamId }
                                ?.name.orEmpty(),
                            color = AppTextBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )

                Tab(
                    selected = !uiState.team,
                    onClick = { viewModel.onChangeTeam(false) },
                    text = {
                        Text(
                            uiState.listOfTeams
                                .firstOrNull { it.id.toInt() == uiState.awayTeamId }
                                ?.name.orEmpty(),
                            color = AppTextRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = AppBorderShape.default(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                if (uiState.team) {
                    uiState.roostersHomeTeam.forEach { roster ->
                        CardRooster(
                            {viewModel.eliminarJugador(roster.id)},
                            roster.playerName,
                            roster.jerseyNumber,
                            AppTextBlue,
                            AppBlueTransparent
                        )
                    }
                } else {
                    uiState.roostersAwayTeam.forEach { roster ->
                        CardRooster(
                            {viewModel.eliminarJugador(roster.id)},
                            roster.playerName,
                            roster.jerseyNumber,
                            AppTextRed,
                            AppRedTransparent
                        )
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.onChangeAddPlayer(true)
                },
                modifier = AppModifierButton.default(),
                colors = AppButtonColors.default(),
                shape = AppShapeButton.default()
            ) {
                Text("Agregar Jugador")
            }

            Button(
                onClick = {
                    onNavigateToStartGame(id)
                },
                modifier = AppModifierButton.default(),
                colors = AppButtonColors.success(),
                shape = AppShapeButton.default()
            ) {
                Text("Empezar Juego")
            }

            if (uiState.showDatePicker) {

                DatePickerDialog(
                    onDismissRequest = {
                        viewModel.onChangeShowDatePicker(false)
                    },

                    confirmButton = {
                        TextButton(
                            onClick = {

                                datePickerState.selectedDateMillis?.let { millis ->

                                    val date = Instant
                                        .ofEpochMilli(millis)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()

                                    viewModel.onChangeDatePickerState(
                                        date.toString()
                                    )
                                }

                                viewModel.onChangeShowDatePicker(false)
                            }
                        ) {
                            Text("Aceptar")
                        }
                    },

                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.onChangeShowDatePicker(false)
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            if (uiState.addPlayer) {

                Dialog(
                    onDismissRequest = {
                        viewModel.onChangeAddPlayer(false)
                    }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF3A3028)
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF3A3028)
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 24.dp,
                                    vertical = 28.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = "Agregar Jugador",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(
                                    bottom = 16.dp
                                )
                            )

                            // NOMBRE
                            Text(
                                text = "Nombre del jugador",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    bottom = 0.dp
                                )
                            )

                            OutlinedTextField(
                                value = uiState.playerName ?: "",
                                onValueChange = {
                                    viewModel.onChangePlayerName(it)
                                },
                                label = {
                                    Text(
                                        text = "Nombre",
                                        color = Color.LightGray
                                    )
                                },
                                singleLine = true,
                                colors = AppTextFieldColors.default(),
                                shape = AppBorderShape.default(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 0.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            // NÚMERO DE JERSEY
                            Text(
                                text = "Número de Jersey",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    bottom = 0.dp
                                )
                            )

                            OutlinedTextField(
                                value = uiState.jerseyNumber ?: "",
                                onValueChange = { value ->
                                    if (value.all { it.isDigit() }) {
                                        viewModel.onChangeJerseyNumber(value)
                                    }
                                },
                                label = {
                                    Text(
                                        text = "Número",
                                        color = Color.LightGray
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                colors = AppTextFieldColors.default(),
                                shape = AppBorderShape.default(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 0.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            // BOTONES
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.onChangeAddPlayer(false)
                                        },
                                        colors = AppButtonColors.secondary(),
                                        modifier = Modifier.then(AppModifierButton.default()),
                                        shape = AppShapeButton.default(),
                                    ) {
                                        Text(
                                            text = "Cancelar",
                                            maxLines = 1,
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {

                                    Button(
                                        onClick = {
                                            viewModel.agregarJugador()
                                            viewModel.onChangeAddPlayer(false)
                                        },
                                        colors = AppButtonColors.success(),
                                        modifier = Modifier.then(AppModifierButton.default()),
                                        shape = AppShapeButton.default()
                                    ) {
                                        Text(
                                            text = "Agregar",
                                            maxLines = 1,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}