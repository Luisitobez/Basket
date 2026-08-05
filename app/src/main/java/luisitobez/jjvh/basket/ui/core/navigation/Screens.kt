package luisitobez.jjvh.basket.ui.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface BasketRoute : NavKey

@Serializable
data object Principal : BasketRoute

@Serializable
data object Game : BasketRoute

@Serializable
data object Team : BasketRoute

@Serializable
data object AddTeam : BasketRoute

@Serializable
data object AddGame : BasketRoute

@Serializable
data class ProfileTeam(
    val id: Int
) : BasketRoute