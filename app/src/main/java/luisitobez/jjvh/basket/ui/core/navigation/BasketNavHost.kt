package luisitobez.jjvh.basket.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import luisitobez.jjvh.basket.ui.Screen.Game.GameScreen
import luisitobez.jjvh.basket.ui.Screen.Principal.PrincipalScreen

@Composable
fun BasketNavHost(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Principal)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLast()
            }
        },
        entryProvider = { route ->
            when (route) {
                Principal -> NavEntry(route) {
                    PrincipalScreen(
                        onStartGame = {
                            backStack.add(Game)
                        },
                        modifier = modifier
                    )
                }

                Game -> NavEntry(route) {
                    GameScreen()
                }

                else -> error("Ruta no reconocida: $route")
            }
        }
    )
}