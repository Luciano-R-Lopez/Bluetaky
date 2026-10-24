package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun ConectadosScreen(
    onOpciones: () -> Unit,
    onIrAChats: () -> Unit,
    onIrAComunidad: () -> Unit
) {
    // remember + mutableStateOf: guarda lo que el usuario escribe en el buscador.
    // Cada vez que cambia, Compose vuelve a dibujar la lista filtrada.
    var busqueda by remember { mutableStateOf("") }

    val conectados = listOf("Alberzo Fernande", "lagarto1234", "Luciano")
    val desconectados = listOf("Fernando2", "Fabian Fernando", "Javier Milei")

    val conectadosFiltrados = conectados.filter { it.contains(busqueda, ignoreCase = true) }
    val desconectadosFiltrados = desconectados.filter { it.contains(busqueda, ignoreCase = true) }

    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs(
                    tabActual = "conectados",
                    onConectados = {},
                    onChats = onIrAChats,
                    onComunidad = onIrAComunidad
                )
            }
        },
        bottomBar = {
            BarraDeBusqueda(
                valor = busqueda,
                onValorChange = { busqueda = it },
                etiqueta = "Buscar dispositivo"
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
            Text("Conectados", fontWeight = FontWeight.Bold, color = AzulPrincipal)
            conectadosFiltrados.forEach { nombre ->
                FilaUsuario(nombre, conectado = true)
            }

            Text(
                "Desconectados",
                fontWeight = FontWeight.Bold,
                color = AzulPrincipal,
                modifier = Modifier.padding(top = 16.dp)
            )
            desconectadosFiltrados.forEach { nombre ->
                FilaUsuario(nombre, conectado = false)
            }

            // Aviso cuando la búsqueda no encuentra a nadie
            if (conectadosFiltrados.isEmpty() && desconectadosFiltrados.isEmpty()) {
                Text(
                    "No se encontró ningún dispositivo con ese nombre.",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun FilaUsuario(nombre: String, conectado: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White)
            .padding(12.dp)
    ) {
        Text(nombre, modifier = Modifier.align(Alignment.CenterStart))
        Text(
            if (conectado) "En línea" else "Fuera de línea",
            color = if (conectado) AzulPrincipal else Color.Red,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
