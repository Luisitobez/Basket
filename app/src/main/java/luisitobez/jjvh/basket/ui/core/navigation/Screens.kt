package luisitobez.jjvh.basket.ui.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface BasketRoute : NavKey

@Serializable
data object Principal : BasketRoute

@Serializable
data object Game : BasketRoute