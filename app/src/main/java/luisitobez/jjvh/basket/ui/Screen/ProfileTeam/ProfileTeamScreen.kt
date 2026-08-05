package luisitobez.jjvh.basket.ui.Screen.ProfileTeam

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import luisitobez.jjvh.basket.ui.core.navigation.ProfileTeam
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppButtonColors
import luisitobez.jjvh.basket.ui.theme.AppModifierButton
import luisitobez.jjvh.basket.ui.theme.AppShapeButton
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors
import kotlin.collections.emptyList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState

@Composable
fun ProfileTeamScreen(
    viewModel: ProfileTeamViewModel = hiltViewModel(),
    modifier: Modifier,
    id: Int,
    onback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getTeamById(id)
        viewModel.getGamesByTeamId(id)
    }
    LaunchedEffect(uiState.deleted) {
        if (uiState.deleted) {
            onback()
        }
    }

    Column(
        modifier = modifier
    ) {
        if (uiState.isLoading) {
            Text(text = "Cargando equipo...")
        } else if (uiState.error != null) {
            Text(text = "Error: ${uiState.error}")
        } else {

            Text(
                text = "NOMBRE DEL EQUIPO", modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
            )
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.onChangeName(it) },
                label = { Text("Nombre del equipo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                colors = AppTextFieldColors.default(),
                singleLine = true,
                shape = AppBorderShape.default(),
                enabled = uiState.isEditing
            )

            Text(
                text = "ABREVIATURA DEL EQUIPO",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
            )
            OutlinedTextField(
                value = uiState.shortName,
                onValueChange = { viewModel.onChangeShortName(it) },
                label = { Text("Abreviatura del equipo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                colors = AppTextFieldColors.default(),
                singleLine = true,
                shape = AppBorderShape.default(),
                enabled = uiState.isEditing
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { viewModel.onClickEdit() },
                        modifier = AppModifierButton.default(),
                        shape = AppShapeButton.default(),
                        colors = AppButtonColors.default()
                    ) {
                        Text(text = if (!uiState.isEditing) "Habilitar edicion" else "Deshabilitar edicion")
                    }
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { viewModel.onChangeShowDialog(true) },
                        modifier = AppModifierButton.default(),
                        shape = AppShapeButton.default(),
                        colors = AppButtonColors.secondary()
                    ) {
                        Text(text = "Eliminar equipo")
                    }
                }


            }


            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items( uiState.listGame) { game ->
                    ProfilTeamGameCard(
                        onClick = { viewModel.deleteGame(game) },
                        homeTeam = uiState.listTeams[game.homeTeamId.toInt()] ?: "",
                        awayTeam = uiState.listTeams[game.awayTeamId.toInt()] ?: "",
                        fecha = game.gameDate ?: "",
                        lugar = game.venue ?: ""
                    )
                }
            }




            Button(
                onClick = { onback() },
                modifier = AppModifierButton.default(),
                shape = AppShapeButton.default(),
                colors = AppButtonColors.secondary()
            ) {
                Text(text = "Salir")
            }

            Button(
                onClick = { viewModel.onClickSave() },
                modifier = AppModifierButton.default(),
                shape = AppShapeButton.default(),
                colors = AppButtonColors.success()
            ) {
                Text(text = "Guardar")
            }
        }
        if (uiState.showDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.onChangeShowDialog(false) },
                title = { Text(text = "Eliminar equipo ${uiState.name}") },
                text = { Text(text = "¿Estas seguro de eliminar el equipo ${uiState.name}?") },
                confirmButton = {
                    Button(
                        onClick = { viewModel.onClickDelete() },
                        modifier = AppModifierButton.default().then(Modifier.weight(1f)),
                        shape = AppShapeButton.default(),
                        colors = AppButtonColors.secondary()
                    ) {
                        Text(text = "Eliminar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { viewModel.onChangeShowDialog(false) },
                        modifier = AppModifierButton.default().then(Modifier.weight(1f)),
                        shape = AppShapeButton.default(),
                        colors = AppButtonColors.default()
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
    }
}