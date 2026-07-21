package luisitobez.jjvh.basket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import luisitobez.jjvh.basket.ui.core.navigation.BasketNavHost
import luisitobez.jjvh.basket.ui.theme.BasketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BasketNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
