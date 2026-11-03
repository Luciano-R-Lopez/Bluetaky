package com.example.bluetaky.presentation.chats

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.domain.Chat
import com.example.bluetaky.ui.BarraDeBusqueda
import com.example.bluetaky.ui.BarraTabs
import com.example.bluetaky.ui.Colores
import com.example.bluetaky.ui.Paleta
import com.example.bluetaky.ui.EncabezadoPrincipal
import com.example.bluetaky.ui.Tab

/**
 * Observa el ViewModel y le reenvía la búsqueda. Abrir un chat, cambiar de pestaña
 * u abrir Opciones se avisa hacia afuera para que el grafo elija la ruta.
 */
@Composable
fun ChatsRoute(
    viewModel: ChatsViewModel,
    paleta: Paleta,
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAComunidad: () -> Unit,
    onAbrirChat: (Int) -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    ChatsScreen(
        estado = estado,
        paleta = paleta,
        onBusqueda = viewModel::cambiarBusqueda,
        onOpciones = onOpciones,
        onIrAConectados = onIrAConectados,
        onIrAComunidad = onIrAComunidad,
        onAbrirChat = onAbrirChat
    )
}

/**
 * Dibuja [estado] y nada más. No filtra: la lista ya llega filtrada.
 * Al tocar un chat avisa su id hacia afuera, para que el grafo abra esa conversación.
 * Cada fila es un Button porque ya trae onClick.
 */
@Composable
fun ChatsScreen(
    paleta: Paleta,
    estado: ChatsUiState,
    onBusqueda: (String) -> Unit,
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAComunidad: () -> Unit,
    onAbrirChat: (Int) -> Unit
) {
    Scaffold(
        containerColor = paleta.fondo,
        topBar = {
            Column {
                EncabezadoPrincipal(paleta, estado.nombreUsuario, onOpciones)
                BarraTabs(
                    paleta = paleta,
                    tabActual = Tab.CHATS,
                    onConectados = onIrAConectados,
                    onChats = {},
                    onComunidad = onIrAComunidad
                )
            }
        },
        bottomBar = {
            BarraDeBusqueda(
                paleta = paleta,
                valor = estado.busqueda,
                onValorChange = onBusqueda,
                etiqueta = "Buscar chat"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(paleta.fondo)
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            estado.chats.forEach { chat ->
                Button(
                    onClick = { onAbrirChat(chat.id) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = paleta.superficie,
                        contentColor = paleta.texto
                    )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(chat.nombre, fontWeight = FontWeight.Bold, color = paleta.principal)
                        Text(chat.ultimoMensaje, fontSize = 13.sp, color = paleta.textoSecundario)
                    }
                }
            }

            if (estado.chats.isEmpty()) {
                Text(
                    "No hay chats que coincidan con la búsqueda.",
                    color = paleta.textoSecundario,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatsPreview() {
    ChatsScreen(
        paleta = Colores.Clara,
        estado = ChatsUiState(
            nombreUsuario = "German Lantaño",
            chats = listOf(Chat(1, "Luciano", "Dame todo lo que me debes"))
        ),
        onBusqueda = {},
        onOpciones = {},
        onIrAConectados = {},
        onIrAComunidad = {},
        onAbrirChat = {}
    )
}
