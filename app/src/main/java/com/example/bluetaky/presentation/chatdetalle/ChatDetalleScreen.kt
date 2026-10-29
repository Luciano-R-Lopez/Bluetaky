package com.example.bluetaky.presentation.chatdetalle

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.domain.Mensaje
import com.example.bluetaky.ui.BarraDeMensaje
import com.example.bluetaky.ui.BurbujaMensaje
import com.example.bluetaky.ui.Colores

/**
 * Observa el ViewModel y le reenvía lo que el usuario escribe y envía.
 * "Volver" se avisa hacia afuera: el grafo hace popBackStack().
 */
@Composable
fun ChatDetalleRoute(
    viewModel: ChatDetalleViewModel,
    onVolver: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    ChatDetalleScreen(
        estado = estado,
        onTexto = viewModel::cambiarTexto,
        onEnviar = viewModel::enviar,
        onVolver = onVolver
    )
}

/**
 * Dibuja [estado] y nada más. No decide si un mensaje se puede enviar.
 */
@Composable
fun ChatDetalleScreen(
    estado: ChatDetalleUiState,
    onTexto: (String) -> Unit,
    onEnviar: () -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(Colores.AzulPrincipal).padding(12.dp)) {
                Button(
                    onClick = onVolver,
                    modifier = Modifier.align(Alignment.CenterStart),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colores.AzulOscuro,
                        contentColor = Color.White
                    )
                ) { Text("Volver") }
                Text(
                    estado.nombreInterlocutor,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            BarraDeMensaje(
                texto = estado.texto,
                onTextoChange = onTexto,
                onEnviar = onEnviar
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
            estado.mensajes.forEach { mensaje ->
                BurbujaMensaje(mensaje, mostrarAutor = false)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatDetallePreview() {
    ChatDetalleScreen(
        estado = ChatDetalleUiState(
            nombreInterlocutor = "Fabian Fernando",
            mensajes = listOf(
                Mensaje("Fabian Fernando", "Dale, nos vemos ahi", false),
                Mensaje("Yo", "Si, ahi voy a estar", true)
            )
        ),
        onTexto = {},
        onEnviar = {},
        onVolver = {}
    )
}
