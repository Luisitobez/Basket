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
            focusedTextColor = AppTextPrimary,
            unfocusedTextColor = AppTextPrimary,
            focusedContainerColor = AppSurface.copy(alpha = 0.55f),
            unfocusedContainerColor = AppSurface.copy(alpha = 0.55f),
            focusedIndicatorColor = PrimaryOrange,
            unfocusedIndicatorColor = PrimaryOrange,
            focusedLabelColor = AppTextSecondary,
            unfocusedLabelColor = AppTextSecondary,
            cursorColor = PrimaryOrange,
            focusedLeadingIconColor = PrimaryOrange,
            unfocusedLeadingIconColor = PrimaryOrange,
            focusedTrailingIconColor = PrimaryOrange,
            unfocusedTrailingIconColor = PrimaryOrange,
            focusedPlaceholderColor = AppTextSecondary,
            unfocusedPlaceholderColor = AppTextSecondary
        )
    }
}

object AppButtonColors {
    @Composable
    fun default(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = AppTextPrimary
        )
    }
    @Composable
    fun secondary(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = AppBackColor,
            contentColor = AppTextPrimary
        )
    }
    @Composable
    fun success(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = AppSuccesColor,
            contentColor = AppTextPrimary
        )
    }
    @Composable
    fun transparent(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = AppTextPrimary
        )
    }
}

object AppCardColors {
    @Composable
    fun default(): CardColors {
        return CardDefaults.cardColors(
            containerColor = AppSurface,
        )
    }
}

object AppBorderShape {
    @Composable
    fun default(): RoundedCornerShape {
        return RoundedCornerShape(16.dp)
    }
    @Composable
    fun small(): RoundedCornerShape {
        return RoundedCornerShape(4.dp)
    }
    @Composable
    fun any(): RoundedCornerShape {
        return RoundedCornerShape(0.dp)
    }
}

object AppShapeButton {
    @Composable
    fun default(): RoundedCornerShape {
        return RoundedCornerShape(
            8.dp,
            24.dp,
            8.dp,
            24.dp
        )
    }
}

object AppModifierCard {
    @Composable
    fun default(): Modifier {
        return Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AppBorder,
                shape = AppBorderShape.default()
            )
    }
}

object AppBorderButtonShape {
    @Composable
    fun default(): Modifier {
        return Modifier.border(
            width = 2.dp,
            color = PrimaryOrange,
            shape = AppShapeButton.default()
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
            width = 1.dp,
            color = PrimaryOrange,
            shape = AppBorderShape.default()
        )
    }
}