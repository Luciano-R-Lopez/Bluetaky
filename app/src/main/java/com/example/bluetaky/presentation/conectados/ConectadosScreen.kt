package com.example.bluetaky.presentation.conectados

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.domain.Contacto
import com.example.bluetaky.ui.BarraDeBusqueda
import com.example.bluetaky.ui.BarraTabs
import com.example.bluetaky.ui.Colores
import com.example.bluetaky.ui.EncabezadoPrincipal
import com.example.bluetaky.ui.Tab

/**
 * Observa el ViewModel y le reenvía la búsqueda. Cambiar de pestaña u abrir Opciones
 * se avisa hacia afuera para que el grafo de navegación elija la ruta.
 */
@Composable
fun ConectadosRoute(
    viewModel: ConectadosViewModel,
    onOpciones: () -> Unit,
    onIrAChats: () -> Unit,
    onIrAComunidad: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    ConectadosScreen(
        estado = estado,
        onBusqueda = viewModel::cambiarBusqueda,
        onOpciones = onOpciones,
        onIrAChats = onIrAChats,
        onIrAComunidad = onIrAComunidad
    )
}

/**
 * Dibuja [estado] y nada más. No filtra: la lista ya llega filtrada.
 */
@Composable
fun ConectadosScreen(
    estado: ConectadosUiState,
    onBusqueda: (String) -> Unit,
    onOpciones: () -> Unit,
    onIrAChats: () -> Unit,
    onIrAComunidad: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(estado.nombreUsuario, onOpciones)
                BarraTabs(
                    tabActual = Tab.CONECTADOS,
                    onConectados = {},
                    onChats = onIrAChats,
                    onComunidad = onIrAComunidad
                )
            }
        },
        bottomBar = {
            BarraDeBusqueda(
                valor = estado.busqueda,
                onValorChange = onBusqueda,
                etiqueta = "Buscar dispositivo"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Colores.FondoClaro)
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            Text("Conectados", fontWeight = FontWeight.Bold, color = Colores.AzulPrincipal)
            estado.conectados.forEach { contacto ->
                FilaUsuario(contacto.nombre, conectado = true)
            }

            Text(
                "Desconectados",
                fontWeight = FontWeight.Bold,
                color = Colores.AzulPrincipal,
                modifier = Modifier.padding(top = 16.dp)
            )
            estado.desconectados.forEach { contacto ->
                FilaUsuario(contacto.nombre, conectado = false)
            }

            if (estado.sinResultados) {
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
            color = if (conectado) Colores.AzulPrincipal else Color.Red,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConectadosPreview() {
    ConectadosScreen(
        estado = ConectadosUiState(
            nombreUsuario = "German Lantaño",
            conectados = listOf(Contacto("Luciano", true)),
            desconectados = listOf(Contacto("Javier Milei", false))
        ),
        onBusqueda = {},
        onOpciones = {},
        onIrAChats = {},
        onIrAComunidad = {}
    )
}
