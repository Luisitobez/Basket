package luisitobez.jjvh.basket.ui.Screen.AddGame

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors
import luisitobez.jjvh.basket.ui.theme.AppTextPrimary
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamSelector(
    selectedTeam: String,
    teams: List<TeamModel>,
    excludedTeamId: Int?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onTeamSelected: (Int) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedTeam,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text("Seleccionar equipo")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.SportsBasketball,
                    contentDescription = null,
                    tint = PrimaryOrange
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            shape = AppBorderShape.default(),
            colors = AppTextFieldColors.default()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            teams.filter { it.id.toInt() != excludedTeamId }.forEach { team ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = team.name,
                            color = AppTextPrimary
                        )
                    },
                    onClick = {
                        onTeamSelected(team.id.toInt())
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}