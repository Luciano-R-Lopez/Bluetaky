package com.example.bluetaky.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetaky.ui.Colores

/**
 * Pantalla de bienvenida. No tiene ViewModel porque no muestra datos que cambien
 * ni aplica lógica: "Comenzar" se avisa hacia afuera y el grafo de navegación
 * elige la ruta.
 */
@Composable
fun SplashScreen(onComenzar: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Colores.AzulPrincipal),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("bt", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text("bluetaky", color = Color.White, fontSize = 22.sp)
            Button(
                onClick = onComenzar,
                modifier = Modifier.padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Colores.AzulPrincipal
                )
            ) { Text("Comenzar") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    SplashScreen(onComenzar = {})
}
