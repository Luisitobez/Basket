package luisitobez.jjvh.basket.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppTextFieldColors {
    @Composable
    fun default(): TextFieldColors {
        return TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.LightGray,

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,

            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,

            cursorColor = Color.Blue
        )
    }
}

object AppButtonColors {
    @Composable
    fun default(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0606DA),
            contentColor = Color.White
        )
    }
}

object AppCardColors {
    @Composable
    fun default(): CardColors {
        return CardDefaults.cardColors(
            containerColor = Color(0xFF0606DA),
        )
    }
}
