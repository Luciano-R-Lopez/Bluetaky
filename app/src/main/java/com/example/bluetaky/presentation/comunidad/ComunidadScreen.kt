package com.example.bluetaky.presentation.comunidad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.domain.Mensaje
import com.example.bluetaky.ui.BarraDeMensaje
import com.example.bluetaky.ui.BarraTabs
import com.example.bluetaky.ui.BurbujaMensaje
import com.example.bluetaky.ui.Colores
import com.example.bluetaky.ui.EncabezadoPrincipal
import com.example.bluetaky.ui.Tab

/**
 * Observa el ViewModel y le reenvía lo que el usuario escribe y envía.
 * Cambiar de pestaña u abrir Opciones se avisa hacia afuera para que el grafo elija la ruta.
 */
@Composable
fun ComunidadRoute(
    viewModel: ComunidadViewModel,
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAChats: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    ComunidadScreen(
        estado = estado,
        onTexto = viewModel::cambiarTexto,
        onEnviar = viewModel::enviar,
        onOpciones = onOpciones,
        onIrAConectados = onIrAConectados,
        onIrAChats = onIrAChats
    )
}

/**
 * Dibuja [estado] y nada más. No decide si un mensaje se puede enviar.
 */
@Composable
fun ComunidadScreen(
    estado: ComunidadUiState,
    onTexto: (String) -> Unit,
    onEnviar: () -> Unit,
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAChats: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(estado.nombreUsuario, onOpciones)
                BarraTabs(
                    tabActual = Tab.COMUNIDAD,
                    onConectados = onIrAConectados,
                    onChats = onIrAChats,
                    onComunidad = {}
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
                BurbujaMensaje(mensaje)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComunidadPreview() {
    ComunidadScreen(
        estado = ComunidadUiState(
            nombreUsuario = "German Lantaño",
            mensajes = listOf(
                Mensaje("lagarto1234", "llevo helado!", false),
                Mensaje("German Lantaño", "yo llevo hielo", true)
            )
        ),
        onTexto = {},
        onEnviar = {},
        onOpciones = {},
        onIrAConectados = {},
        onIrAChats = {}
    )
}
