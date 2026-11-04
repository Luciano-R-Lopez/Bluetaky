package com.example.bluetaky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetaky.domain.Mensaje

/**
 * Componentes visuales que se repiten en varias pantallas.
 * No tienen estado propio ni conocen ViewModels: reciben todo por parámetro,
 * incluida la [Paleta] con la que se pintan.
 */

/** Pestaña resaltada en [BarraTabs]. Es solo un dato visual, no una ruta. */
enum class Tab { CONECTADOS, CHATS, COMUNIDAD }

/**
 * Los TextField de Material pintan con el tema por defecto (siempre claro).
 * Acá se les indica la paleta para que también se vean bien en modo oscuro.
 */
@Composable
fun coloresDeCampo(paleta: Paleta) = TextFieldDefaults.colors(
    focusedContainerColor = paleta.campo,
    unfocusedContainerColor = paleta.campo,
    focusedTextColor = paleta.texto,
    unfocusedTextColor = paleta.texto,
    focusedLabelColor = paleta.textoSecundario,
    unfocusedLabelColor = paleta.textoSecundario,
    cursorColor = paleta.acento
)

@Composable
fun EncabezadoPrincipal(paleta: Paleta, nombre: String, onOpciones: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(paleta.principal).padding(12.dp)) {
        Text(
            nombre,
            color = paleta.sobrePrincipal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Button(
            onClick = onOpciones,
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(
                containerColor = paleta.principalOscuro,
                contentColor = paleta.sobrePrincipal
            )
        ) { Text("Opciones") }
    }
}

@Composable
fun BarraTabs(
    paleta: Paleta,
    tabActual: Tab,
    onConectados: () -> Unit,
    onChats: () -> Unit,
    onComunidad: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().background(paleta.principalOscuro)) {
        TabBoton(paleta, "Conectados", tabActual == Tab.CONECTADOS, onConectados, Modifier.weight(1f))
        TabBoton(paleta, "Chats", tabActual == Tab.CHATS, onChats, Modifier.weight(1f))
        TabBoton(paleta, "Comunidad", tabActual == Tab.COMUNIDAD, onComunidad, Modifier.weight(1f))
    }
}

/** Recibe [modifier] para que [BarraTabs] pueda aplicarle weight(1f) desde el Row. */
@Composable
private fun TabBoton(
    paleta: Paleta,
    texto: String,
    activo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (activo) paleta.tabActivo else paleta.principalOscuro,
            contentColor = if (activo) paleta.textoTabActivo else paleta.sobrePrincipal
        )
    ) { Text(texto, fontSize = 12.sp) }
}

/** Buscador. El texto que escribe el usuario sube a la pantalla con [onValorChange]. */
@Composable
fun BarraDeBusqueda(
    paleta: Paleta,
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String
) {
    Box(modifier = Modifier.fillMaxWidth().background(paleta.superficie).padding(8.dp)) {
        TextField(
            value = valor,
            onValueChange = onValorChange,
            label = { Text(etiqueta) },
            singleLine = true,
            colors = coloresDeCampo(paleta),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Campo de mensaje + botón de envío, usado en Comunidad y en el detalle de chat.
 * navigationBarsPadding() lo separa de la barra de gestos y imePadding() lo sube cuando
 * aparece el teclado. Van después del fondo para que el color cubra también ese espacio.
 */
@Composable
fun BarraDeMensaje(
    paleta: Paleta,
    texto: String,
    onTextoChange: (String) -> Unit,
    onEnviar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(paleta.superficie)
            .navigationBarsPadding()
            .imePadding()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = texto,
            onValueChange = onTextoChange,
            label = { Text("mensaje") },
            singleLine = true,
            colors = coloresDeCampo(paleta),
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onEnviar,
            modifier = Modifier.padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = paleta.principal,
                contentColor = paleta.sobrePrincipal
            )
        ) { Text("Enviar") }
    }
}

/**
 * Burbuja de mensaje: a la derecha si es propio, a la izquierda si es recibido.
 * El autor va en negrita sobre el texto. Con [mostrarAutor] = false se omite; en un chat de a dos alcanza
 * con la posición de la burbuja.
 */
@Composable
fun BurbujaMensaje(paleta: Paleta, mensaje: Mensaje, mostrarAutor: Boolean = true) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .background(if (mensaje.propio) paleta.principal else paleta.superficie)
                .padding(12.dp)
                .align(if (mensaje.propio) Alignment.CenterEnd else Alignment.CenterStart)
        ) {
            val colorTexto = if (mensaje.propio) paleta.sobrePrincipal else paleta.texto
            Column {
                if (mostrarAutor) {
                    Text(mensaje.autor, color = colorTexto, fontWeight = FontWeight.Bold)
                }
                Text(mensaje.texto, color = colorTexto)
            }
        }
    }
}
