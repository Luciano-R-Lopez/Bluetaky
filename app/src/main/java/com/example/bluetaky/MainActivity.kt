package com.example.bluetaky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bluetaky.navigation.BluetakyNavHost

/**
 * Una sola Activity. No carga datos, no filtra y no conoce las rutas:
 * pone el grafo de navegación en pantalla.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetakyNavHost()
        }
    }
}
