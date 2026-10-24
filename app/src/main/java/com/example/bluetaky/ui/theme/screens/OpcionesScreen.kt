package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OpcionesScreen(onVolver: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text("Opciones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = AzulPrincipal)

        listOf(
            "Cambiar a dark mode" to "OFF",
            "Volumen" to "50%",
            "Vibración" to "SI",
            "Notificaciones" to "SI"
        ).forEach { (etiqueta, valor) ->
            FilaOpcion(etiqueta, valor)
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal,
                contentColor = Color.White
            )
        ) { Text("Volver") }
    }
}

@Composable
private fun FilaOpcion(etiqueta: String, valor: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(FondoClaro)
            .padding(12.dp)
    ) {
        Text(etiqueta, modifier = Modifier.align(Alignment.CenterStart))
        Text(
            valor,
            fontWeight = FontWeight.Bold,
            color = AzulPrincipal,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
