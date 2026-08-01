package luisitobez.jjvh.basket.ui.Screen.AddTeam

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import luisitobez.jjvh.basket.ui.theme.AppBorderButtonShape
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppButtonColors
import luisitobez.jjvh.basket.ui.theme.AppModifierButton
import luisitobez.jjvh.basket.ui.theme.AppModifierCard
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors

@Composable
fun AddTeamScreen(
    onback: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddTeamViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Text(
                text = "Ingrese el nombre",
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            )
            TextField(
                value = uiState.value.name,
                onValueChange = { viewModel.onChangeName(it) },
                label = { Text("Nombre del equipo") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                colors = AppTextFieldColors.default(),
                singleLine = true
            )

            Text(
                text = "Ingrese el nombre corto",
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )
            TextField(
                value = uiState.value.shortName,
                onValueChange = { viewModel.onChangeShortName(it) },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                label = { Text("Nombre corto del equipo") },
                colors = AppTextFieldColors.default(),
                singleLine = true
            )

            Button(
                onClick = { viewModel.putTeam(onback) },
                modifier = AppModifierButton.default(),
                colors = AppButtonColors.default(),
                shape = AppBorderShape.default()
            ){
                Text(text = "Agregar equipo")
            }
        }
    }
}