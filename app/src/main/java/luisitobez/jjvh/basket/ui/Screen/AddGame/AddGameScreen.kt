package luisitobez.jjvh.basket.ui.Screen.AddGame

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppButtonColors
import luisitobez.jjvh.basket.ui.theme.AppModifierButton
import luisitobez.jjvh.basket.ui.theme.AppShapeButton
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange
import java.time.Instant
import java.time.ZoneId


@Composable
fun AddGameScreen(
    viewModel: AddGameViewModel = hiltViewModel(),
    onAddGameClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->

            val date = Instant
                .ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            viewModel.onChangeDatePickerState(
                date.toString()
            )
        }
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
                    selectedTeam = uiState.listOfTeams.collectAsState(initial = listOf()).value
                        .find { it.id.toInt() == uiState.homeTeamId }
                        ?.name
                        ?: "Seleccionar equipo",

                    teams = uiState.listOfTeams.collectAsState(initial = listOf()).value,

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
                    selectedTeam = uiState.listOfTeams.collectAsState(initial = listOf()).value
                        .find { it.id.toInt() == uiState.awayTeamId }
                        ?.name
                        ?: "Seleccionar equipo",

                    teams = uiState.listOfTeams.collectAsState(initial = listOf()).value,

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
                text = "JUEGO",
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
                placeholder = {
                    Text("Ingrese lugar")
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
                    placeholder = {
                        Text("Fecha")
                    },
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
                placeholder = {
                    Text("Escribe una nota...")
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

            Button(
                onClick = {
                    viewModel.onClickeAddGame()
                    onAddGameClick()
                },
                modifier = AppModifierButton.default(),
                shape = AppShapeButton.default(),
                colors = AppButtonColors.default()
            ) {
                Text(text = "Agregar juego")
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

        }
    }
}