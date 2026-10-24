package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatsScreen(
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAComunidad: () -> Unit,
    onAbrirChat: () -> Unit
) {
    // Texto del buscador de chats
    var busqueda by remember { mutableStateOf("") }

    val chats = listOf(
        "Fabian Fernando" to "Todo bien amigo? sigue en pie lo de la juntada?",
        "Luciano" to "Dame todo lo que me debes",
        "Fernando2" to "viste que hay alguien que se llama como yo?",
        "lagarto1234" to "grabamos un video?",
        "Javier Milei" to "ahi te pague todo",
        "Alberzo Fernande" to "Decime algo lindo"
    )

    // Filtra por nombre o por el contenido del último mensaje
    val chatsFiltrados = chats.filter { (nombre, mensaje) ->
        nombre.contains(busqueda, ignoreCase = true) ||
            mensaje.contains(busqueda, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs(
                    tabActual = "chats",
                    onConectados = onIrAConectados,
                    onChats = {},
                    onComunidad = onIrAComunidad
                )
            }
        },
        bottomBar = {
            BarraDeBusqueda(
                valor = busqueda,
                onValorChange = { busqueda = it },
                etiqueta = "Buscar chat"
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
            chatsFiltrados.forEach { (nombre, mensaje) ->
                // Button: cada fila es clickeable porque Button ya trae onClick
                Button(
                    onClick = onAbrirChat,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(nombre, fontWeight = FontWeight.Bold, color = AzulPrincipal)
                        Text(mensaje, fontSize = 13.sp, color = Color.DarkGray)
                    }
                }
            }

            if (chatsFiltrados.isEmpty()) {
                Text(
                    "No hay chats que coincidan con la búsqueda.",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
