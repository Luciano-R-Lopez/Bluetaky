package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ChatDetalleScreen(onVolver: () -> Unit) {
    var texto by remember { mutableStateOf("") }

    var mensajes by remember {
        mutableStateOf(
            listOf(
                Mensaje("Fabian Fernando", "Todo bien amigo? sigue en pie lo de la juntada?", false),
                Mensaje("Yo", "Si, ahi voy a estar", true),
                Mensaje("Fabian Fernando", "Dale, nos vemos ahi", false)
            )
        )
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(AzulPrincipal).padding(12.dp)) {
                Button(
                    onClick = onVolver,
                    modifier = Modifier.align(Alignment.CenterStart),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulOscuro,
                        contentColor = Color.White
                    )
                ) { Text("Volver") }
                Text(
                    "Fabian Fernando",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            BarraDeMensaje(
                texto = texto,
                onTextoChange = { texto = it },
                onEnviar = {
                    if (texto.isNotBlank()) {
                        mensajes = mensajes + Mensaje("Yo", texto, true)
                        texto = ""
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(FondoClaro)
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            // mostrarAutor = false: en un chat de a dos alcanza con la posición de la burbuja
            mensajes.forEach { mensaje ->
                BurbujaMensaje(mensaje, mostrarAutor = false)
            }
        }
    }
}
