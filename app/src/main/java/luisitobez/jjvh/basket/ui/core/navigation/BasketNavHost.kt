package luisitobez.jjvh.basket.ui.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import luisitobez.jjvh.basket.ui.Screen.AddGame.AddGameScreen
import luisitobez.jjvh.basket.ui.Screen.AddTeam.AddTeamScreen
import luisitobez.jjvh.basket.ui.Screen.Game.GameScreen
import luisitobez.jjvh.basket.ui.Screen.Principal.PrincipalScreen
import luisitobez.jjvh.basket.ui.Screen.ProfileTeam.ProfileTeamScreen
import luisitobez.jjvh.basket.ui.Screen.Team.TeamScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun BasketNavHost(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<NavKey>,
) {


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
                            backStack.add(AddGame)
                        },
                        modifier = modifier,
                        onNavigateToGame = {
                            backStack.add(Game)
                        },
                    )
                }

                Game -> NavEntry(route) {
                    GameScreen(
                        modifier = modifier,
                        id = 0
                    )
                }

                Team -> NavEntry(route) {
                    TeamScreen(
                        modifier = modifier,
                        onAddTeamClick = {
                            backStack.add(AddTeam)
                        },
                        onTeamClick = { id ->
                            backStack.add(ProfileTeam(id = id))
                        }
                    )
                }

                AddTeam -> NavEntry(route) {
                    AddTeamScreen(
                        onback = {
                            backStack.removeLast()
                        },
                        modifier = modifier
                    )
                }

                AddGame -> NavEntry(route) {
                    AddGameScreen(
                        onAddGameClick = {
                            backStack.removeLast()
                        },
                        modifier = modifier
                    )
                }
                is ProfileTeam -> NavEntry(route) {
                    ProfileTeamScreen(
                        id = route.id,
                        modifier = modifier,
                        onback = {
                            backStack.removeLast()
                        }
                    )
                }


                else -> error("Ruta no reconocida: $route")
            }
        }
    )
}