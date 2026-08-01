package luisitobez.jjvh.basket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.ui.core.navigation.BasketNavHost
import luisitobez.jjvh.basket.ui.core.navigation.Principal
import luisitobez.jjvh.basket.ui.core.navigation.Team
import luisitobez.jjvh.basket.ui.theme.BasketTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import luisitobez.jjvh.basket.ui.theme.BackGroundColor
import luisitobez.jjvh.basket.ui.theme.ContainerColor
import luisitobez.jjvh.basket.ui.theme.ShapeCardColor
import luisitobez.jjvh.basket.ui.theme.SurfacaColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack(Principal)

            BasketTheme {
                val drawerState = rememberDrawerState(
                    initialValue = DrawerValue.Closed
                )

                val scope = rememberCoroutineScope()

                val backStack = rememberNavBackStack(Principal)

                ModalNavigationDrawer(
                    drawerState = drawerState,

                    drawerContent = {

                        ModalDrawerSheet(
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {

                            Text(
                                text = "Basket",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.headlineSmall
                            )

                            HorizontalDivider()

                            NavigationDrawerItem(
                                label = {
                                    Text("Juegos")
                                },
                                selected = backStack.lastOrNull() == Principal,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }

                                    if (backStack.lastOrNull() != Principal) {
                                        backStack.clear()
                                        backStack.add(Principal)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Inicio"
                                    )
                                }
                            )

                            NavigationDrawerItem(
                                label = {
                                    Text("Equipos")
                                },
                                selected = backStack.lastOrNull() == Team,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }

                                    if (backStack.lastOrNull() != Team) {
                                        backStack.add(Team)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.SportsBasketball,
                                        contentDescription = "Equipos"
                                    )
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = ContainerColor
                                ),
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.DensityMedium,
                                            contentDescription = "Exit",
                                            tint = ShapeCardColor
                                        )
                                    }
                                },
                                title = {},
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(BackGroundColor),
                        ){
                            Image(
                                painter = painterResource(R.drawable._2e85756f633b9787afd437d1c680b75d),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alpha = 0.2f
                            )
                            BasketNavHost(
                                modifier = Modifier.fillMaxSize(),
                                backStack = backStack
                            )
                        }
                    }
                }
            }
        }
    }
}
