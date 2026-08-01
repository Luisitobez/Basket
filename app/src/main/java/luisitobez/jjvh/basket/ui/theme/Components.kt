package luisitobez.jjvh.basket.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AppTextFieldColors {
    @Composable
    fun default(): TextFieldColors {
        return TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.DarkGray,

            focusedContainerColor = Color(0x55F9A825),
            unfocusedContainerColor = Color(0x55F9A825),

            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.DarkGray,

            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.DarkGray,

            cursorColor = Color.Blue
        )
    }
}

object AppButtonColors {
    @Composable
    fun default(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = ContainerColor,
            contentColor = Color.White
        )
    }
}

object AppCardColors {
    @Composable
    fun default(): CardColors {
        return CardDefaults.cardColors(
            containerColor = ContainerColor,
        )
    }
}

object AppBorderShape {
    @Composable
    fun default(): RoundedCornerShape {
        return RoundedCornerShape(15.dp, 50.dp, 15.dp, 50.dp)
    }
}

object AppModifierCard {
    @Composable
    fun default(): Modifier {
        return Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = ShapeCardColor,
                shape = AppBorderShape.default()
            )
    }
}

object AppBorderButtonShape {
    @Composable
    fun default(): Modifier {
        return Modifier.border(
            width = 2.dp,
            color = Color(0xFFF9A825),
            shape = AppBorderShape.default()
        )
    }
}

object AppModifierButton {
    @Composable
    fun default(): Modifier {
        return Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .then(AppBorderButtonShape.default())
    }
}

object AppModifierTextFieldShape {
    @Composable
    fun default(): Modifier {
        return Modifier.border(
            width = 2.dp,
            color = Color(0xFFF9A825),
            shape = RoundedCornerShape(8.dp)
        )
    }
}
