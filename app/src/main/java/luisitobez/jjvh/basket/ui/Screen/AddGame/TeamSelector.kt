package luisitobez.jjvh.basket.ui.Screen.AddGame

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppModifierTextFieldShape
import luisitobez.jjvh.basket.ui.theme.ShapeCardColor

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
            value = selectedTeam, onValueChange = {}, readOnly = true,
            label = {
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor(
                    MenuAnchorType.PrimaryNotEditable
                )
                .fillMaxWidth()
                .then(AppModifierTextFieldShape.default()),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.SportsBasketball,
                    contentDescription = null,
                    tint = ShapeCardColor
                )
            },
        )

        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = {
                onExpandedChange(false)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            teams.filter { it.id.toInt() != excludedTeamId }.forEach { team ->

                DropdownMenuItem(text = {
                    Text(team.name)
                }, onClick = {
                    onTeamSelected(team.id.toInt())
                    onExpandedChange(false)
                })
            }
        }
    }
}