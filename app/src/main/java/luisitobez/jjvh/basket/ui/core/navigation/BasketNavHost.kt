package luisitobez.jjvh.basket.ui.core.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import luisitobez.jjvh.basket.ui.Screen.AddGame.AddGameScreen
import luisitobez.jjvh.basket.ui.Screen.StartGame.StartGameScreen
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
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { route ->
            when (route) {
                Principal -> NavEntry(route) {
                    PrincipalScreen(
                        onStartGame = {
                            backStack.add(AddGame)
                        },
                        modifier = modifier,
                        onNavigateToGame = { id ->
                            backStack.add(Game(id))
                        },
                    )
                }

                is Game -> NavEntry(route) {
                    GameScreen(
                        modifier = modifier,
                        id = route.id.toInt(),
                        onNavigateToStartGame = { id ->
                            backStack.add(StartGame(id))
                        }
                    )
                }

                Team -> NavEntry(route) {
                    TeamScreen(
                        modifier = modifier,
                        onAddTeamClick = {
                            backStack.add(AddTeam)
                        },
                        onTeamClick = { id ->
                            // eliminar ProfileTeam anterior si existe arriba, para no acumular estados
                            while (backStack.lastOrNull() is ProfileTeam) {
                                backStack.removeLast()
                            }
                            backStack.add(ProfileTeam(id))
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
                            Log.d("NAV", "Antes: ${backStack.size}")
                            backStack.removeLast()
                            Log.d("NAV", "Después: ${backStack.size}")
                        }
                    )
                    print(backStack)
                }

                is StartGame -> NavEntry(route) {
                    StartGameScreen(
                        id = route.id,
                        modifier = modifier
                    )
                }


                else -> error("Ruta no reconocida: $route")
            }
        }
    )
}