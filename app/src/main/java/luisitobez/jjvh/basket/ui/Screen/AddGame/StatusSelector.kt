package luisitobez.jjvh.basket.ui.Screen.AddGame

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
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
import luisitobez.jjvh.basket.ui.theme.AppBorderShape
import luisitobez.jjvh.basket.ui.theme.AppTextFieldColors
import luisitobez.jjvh.basket.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusSelector(
    selectedStatus: String?,
    statuses: Map<String, String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onStatusSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedStatus
                ?.let { statuses[it] }
                ?: "Seleccionar estado",

            onValueChange = {},
            readOnly = true,

            label = {
                Text("Estado")
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
                .fillMaxWidth(),
            colors = AppTextFieldColors.default(),
            shape = AppBorderShape.default(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AddTask,
                    contentDescription = null,
                    tint = PrimaryOrange
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(false)
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            statuses.forEach { (key, value) ->

                DropdownMenuItem(
                    text = {
                        Text(value)
                    },
                    onClick = {
                        onStatusSelected(key)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}