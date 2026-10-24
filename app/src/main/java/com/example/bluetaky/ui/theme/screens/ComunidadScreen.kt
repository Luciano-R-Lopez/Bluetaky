package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ComunidadScreen(
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAChats: () -> Unit
) {
    // Texto que se está escribiendo en la barra inferior
    var texto by remember { mutableStateOf("") }

    // Lista de mensajes del canal. Al ser estado, agregar uno redibuja la pantalla.
    var mensajes by remember {
        mutableStateOf(
            listOf(
                Mensaje("Alberzo Fernande", "sale joda en olivos, lleven bebida", false),
                Mensaje("lagarto1234", "llevo helado!", false),
                Mensaje("Luciano", "tengo limones y coca", false)
            )
        )
    }

    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs(
                    tabActual = "comunidad",
                    onConectados = onIrAConectados,
                    onChats = onIrAChats,
                    onComunidad = {}
                )
            }
        },
        bottomBar = {
            BarraDeMensaje(
                texto = texto,
                onTextoChange = { texto = it },
                onEnviar = {
                    // Solo envía si hay algo escrito
                    if (texto.isNotBlank()) {
                        mensajes = mensajes + Mensaje(Sesion.nombre, texto, true)
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
            mensajes.forEach { mensaje ->
                BurbujaMensaje(mensaje)
            }
        }
    }
}
