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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import luisitobez.jjvh.basket.ui.theme.AppBorderButtonShape
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppButtonColors
import luisitobez.jjvh.basket.ui.theme.AppModifierButton
import luisitobez.jjvh.basket.ui.theme.AppModifierCard
import luisitobez.jjvh.basket.ui.theme.AppShapeButton
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors

@Composable
fun AddTeamScreen(
    onback: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddTeamViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
    ) {
        item {

            Text(
                text = "NOMBRE DEL EQUIPO",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
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
                shape = AppBorderShape.default()
            )

            Text(
                text = "ABREVIATURA DEL EQUIPO",
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
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
                shape = AppBorderShape.default()
            )

            Button(
                onClick = { viewModel.putTeam(onback) },
                modifier = AppModifierButton.default(),
                shape = AppShapeButton.default(),
                colors = AppButtonColors.default()
            ) {
                Text(text = "Agregar equipo")
            }
        }
    }
}