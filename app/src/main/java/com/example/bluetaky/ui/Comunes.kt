package com.example.bluetaky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetaky.domain.Mensaje

/**
 * Componentes visuales que se repiten en varias pantallas.
 * No tienen estado propio ni conocen ViewModels: reciben todo por parámetro.
 */

/** Pestaña resaltada en [BarraTabs]. Es solo un dato visual, no una ruta. */
enum class Tab { CONECTADOS, CHATS, COMUNIDAD }

@Composable
fun EncabezadoPrincipal(nombre: String, onOpciones: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(Colores.AzulPrincipal).padding(12.dp)) {
        Text(
            nombre,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Button(
            onClick = onOpciones,
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(containerColor = Colores.AzulOscuro, contentColor = Color.White)
        ) { Text("Opciones") }
    }
}

@Composable
fun BarraTabs(
    tabActual: Tab,
    onConectados: () -> Unit,
    onChats: () -> Unit,
    onComunidad: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().background(Colores.AzulOscuro)) {
        TabBoton("Conectados", tabActual == Tab.CONECTADOS, onConectados, Modifier.weight(1f))
        TabBoton("Chats", tabActual == Tab.CHATS, onChats, Modifier.weight(1f))
        TabBoton("Comunidad", tabActual == Tab.COMUNIDAD, onComunidad, Modifier.weight(1f))
    }
}

/** Recibe [modifier] para que [BarraTabs] pueda aplicarle weight(1f) desde el Row. */
@Composable
private fun TabBoton(
    texto: String,
    activo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (activo) Color.White else Colores.AzulOscuro,
            contentColor = if (activo) Colores.AzulPrincipal else Color.White
        )
    ) { Text(texto, fontSize = 12.sp) }
}

/** Buscador. El texto que escribe el usuario sube a la pantalla con [onValorChange]. */
@Composable
fun BarraDeBusqueda(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String
) {
    Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp)) {
        TextField(
            value = valor,
            onValueChange = onValorChange,
            label = { Text(etiqueta) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/** Campo de mensaje + botón de envío, usado en Comunidad y en el detalle de chat. */
@Composable
fun BarraDeMensaje(
    texto: String,
    onTextoChange: (String) -> Unit,
    onEnviar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = texto,
            onValueChange = onTextoChange,
            label = { Text("mensaje") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onEnviar,
            modifier = Modifier.padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Colores.AzulPrincipal, contentColor = Color.White)
        ) { Text("Enviar") }
    }
}

/**
 * Burbuja de mensaje: a la derecha si es propio, a la izquierda si es recibido.
 * Con [mostrarAutor] = false se omite el nombre; en un chat de a dos alcanza
 * con la posición de la burbuja.
 */
@Composable
fun BurbujaMensaje(mensaje: Mensaje, mostrarAutor: Boolean = true) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .background(if (mensaje.propio) Colores.AzulPrincipal else Color.White)
                .padding(12.dp)
                .align(if (mensaje.propio) Alignment.CenterEnd else Alignment.CenterStart)
        ) {
            if (mostrarAutor) {
                Text(
                    "${mensaje.autor}: ${mensaje.texto}",
                    color = if (mensaje.propio) Color.White else Colores.AzulOscuro
                )
            } else {
                Text(
                    mensaje.texto,
                    color = if (mensaje.propio) Color.White else Color.Black
                )
            }
        }
    }
}
