package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores reutilizados en toda la app (parámetro del Modifier.background)
val AzulPrincipal = Color(0xFF3A6EA5)
val AzulOscuro = Color(0xFF2C5680)
val FondoClaro = Color(0xFFF2F2F2)
val CampoGris = Color(0xFFE3E3E3)

@Composable
fun SplashScreen(onComenzar: () -> Unit) {
    // Box: superpone y centra el contenido sobre el fondo azul
    Box(
        modifier = Modifier.fillMaxSize().background(AzulPrincipal),
        contentAlignment = Alignment.Center
    ) {
        // Column: acomoda el logo y el botón, uno debajo del otro
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("bt", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text("bluetaky", color = Color.White, fontSize = 22.sp)
            // Button: dispara la navegación hacia "perfil"
            Button(
                onClick = onComenzar,
                modifier = Modifier.padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = AzulPrincipal)
            ) { Text("Comenzar") }
        }
    }
}

@Composable
fun PerfilScreen(onAceptar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar: Box de tamaño fijo (size) con fondo gris, sin TextField ni imagen real
        Box(
            modifier = Modifier.padding(top = 24.dp).size(100.dp).background(CampoGris),
            contentAlignment = Alignment.Center
        ) { Text("👤", fontSize = 40.sp) }

        // Placeholder de campo "Nombre" (TextField no está en el temario)
        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp).background(CampoGris).padding(12.dp)
        ) { Text("Nombre") }

        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).background(CampoGris).padding(12.dp)
        ) { Text("Estado (opcional)") }

        Text(
            "Al usar esta aplicación aceptás nuestras políticas y condiciones de uso.",
            color = AzulPrincipal,
            fontSize = 13.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        Button(
            onClick = onAceptar,
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal, contentColor = Color.White)
        ) { Text("Aceptar") }
    }
}

// Encabezado reutilizado por las 3 pantallas con pestañas
@Composable
private fun EncabezadoPrincipal(onOpciones: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(AzulPrincipal).padding(12.dp)) {
        Text(
            "German Lantaño",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Button(
            onClick = onOpciones,
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(containerColor = AzulOscuro, contentColor = Color.White)
        ) { Text("Opciones") }
    }
}

@Composable
private fun BarraTabs(
    tabActual: String,
    onConectados: () -> Unit,
    onChats: () -> Unit,
    onComunidad: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().background(AzulOscuro)) {
        TabBoton("Conectados", tabActual == "conectados", onConectados, Modifier.weight(1f))
        TabBoton("Chats", tabActual == "chats", onChats, Modifier.weight(1f))
        TabBoton("Comunidad", tabActual == "comunidad", onComunidad, Modifier.weight(1f))
    }
}

@Composable
private fun TabBoton(texto: String, activo: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (activo) Color.White else AzulOscuro,
            contentColor = if (activo) AzulPrincipal else Color.White
        )
    ) { Text(texto, fontSize = 12.sp) }
}

@Composable
fun ConectadosScreen(
    onOpciones: () -> Unit,
    onIrAChats: () -> Unit,
    onIrAComunidad: () -> Unit
) {
    // Scaffold: separa la barra superior (topBar) del contenido
    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs("conectados", onConectados = {}, onChats = onIrAChats, onComunidad = onIrAComunidad)
            }
        }
    ) { innerPadding ->
        // verticalScroll: permite desplazar la lista si no entra en pantalla
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(FondoClaro)
                .verticalScroll(rememberScrollState()).padding(12.dp)
        ) {
            Text("Conectados", fontWeight = FontWeight.Bold, color = AzulPrincipal)
            listOf("Alberzo Fernande", "lagarto1234", "Luciano").forEach { nombre ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Color.White).padding(12.dp)
                ) {
                    Text(nombre, modifier = Modifier.align(Alignment.CenterStart))
                    Text("🟢", modifier = Modifier.align(Alignment.CenterEnd))
                }
            }
            Text(
                "Desconectados", fontWeight = FontWeight.Bold, color = AzulPrincipal,
                modifier = Modifier.padding(top = 16.dp)
            )
            listOf("Fernando2", "Favian Fernando", "Javier Milei").forEach { nombre ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Color.White).padding(12.dp)
                ) {
                    Text(nombre, modifier = Modifier.align(Alignment.CenterStart))
                    Text("✕", color = Color.Red, modifier = Modifier.align(Alignment.CenterEnd))
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp).background(CampoGris).padding(12.dp)
            ) { Text("buscar 🔍") }
        }
    }
}

@Composable
fun ChatsScreen(
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAComunidad: () -> Unit,
    onAbrirChat: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs("chats", onConectados = onIrAConectados, onChats = {}, onComunidad = onIrAComunidad)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(FondoClaro)
                .verticalScroll(rememberScrollState()).padding(12.dp)
        ) {
            val chats = listOf(
                "Fabian Fernando" to "Todo bien amigo? sigue eso de la juntada teren...",
                "Luciano" to "Dame todo lo que me debes o te pudro",
                "Fernando2" to "vote que hay alguien que se llama como yo?",
                "lagarto1234" to "gralame un video?",
                "Javier Milei" to "afr te pague todo",
                "Alberzo Fernande" to "Decime algo lindo"
            )
            chats.forEach { (nombre, mensaje) ->
                // Button: cada fila es clickeable porque Button trae onClick incorporado
                Button(
                    onClick = onAbrirChat,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(nombre, fontWeight = FontWeight.Bold, color = AzulPrincipal)
                        Text(mensaje, fontSize = 13.sp, color = Color.DarkGray)
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp).background(CampoGris).padding(12.dp)
            ) { Text("buscar chat 🔍") }
        }
    }
}

@Composable
fun ComunidadScreen(
    onOpciones: () -> Unit,
    onIrAConectados: () -> Unit,
    onIrAChats: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                EncabezadoPrincipal(onOpciones)
                BarraTabs("comunidad", onConectados = onIrAConectados, onChats = onIrAChats, onComunidad = {})
            }
        },
        bottomBar = {
            // bottomBar: franja fija con el "input" de mensaje
            Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.8f).background(CampoGris).padding(12.dp)
                        .align(Alignment.CenterStart)
                ) { Text("mensaje") }
                Button(
                    onClick = {}, // o onVolver según el caso
                    modifier = Modifier.align(Alignment.CenterEnd), // o CenterStart
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal, contentColor = Color.White)
                ) { Text("➤") } // o "←"
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(FondoClaro)
                .verticalScroll(rememberScrollState()).padding(12.dp)
        ) {
            Text("Alberzo Fernande → sale joda en olivos, llevan bebida", color = AzulOscuro)
            Text("lagarto1234 → llevo heladooo!", modifier = Modifier.padding(top = 8.dp), color = AzulOscuro)
            Text("Luciano → tengo limones y coca", modifier = Modifier.padding(top = 8.dp), color = AzulOscuro)
        }
    }
}

@Composable
fun OpcionesScreen(onVolver: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text("Opciones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = AzulPrincipal)
        listOf(
            "Cambiar a dark mode" to "OFF",
            "Volumen" to "50%",
            "Vibración" to "SI",
            "Notificaciones" to "SI"
        ).forEach { (label, valor) ->
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).background(FondoClaro).padding(12.dp)
            ) {
                Text(label, modifier = Modifier.align(Alignment.CenterStart))
                Text(valor, fontWeight = FontWeight.Bold, color = AzulPrincipal, modifier = Modifier.align(Alignment.CenterEnd))
            }
        }
        Button(
            onClick = onVolver,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal, contentColor = Color.White)
        ) { Text("Volver") }
    }
}

@Composable
fun ChatDetalleScreen(onVolver: () -> Unit) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(AzulPrincipal).padding(12.dp)) {
                Button(
                    onClick = onVolver,
                    modifier = Modifier.align(Alignment.CenterStart),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulOscuro, contentColor = Color.White)
                ) { Text("←") }
                Text(
                    "Fabian Fernando", color = Color.White, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.8f).background(CampoGris).padding(12.dp)
                        .align(Alignment.CenterStart)
                ) { Text("mensaje") }
                Button(
                    onClick = {}, // o onVolver según el caso
                    modifier = Modifier.align(Alignment.CenterEnd), // o CenterStart
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal, contentColor = Color.White)
                ) { Text("➤") } // o "←"
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(FondoClaro).padding(12.dp)) {
            // Mensaje recibido (él): alineado a la izquierda
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.background(Color.White).padding(12.dp).align(Alignment.CenterStart)
                ) { Text("Todo bien amigo? sigue eso de la juntada") }
            }
            // Mensaje enviado (yo): alineado a la derecha
            Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Box(
                    modifier = Modifier.background(AzulPrincipal).padding(12.dp).align(Alignment.CenterEnd)
                ) { Text("Sí, ahí voy a estar", color = Color.White) }
            }
            // Mensaje recibido (él)
            Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Box(
                    modifier = Modifier.background(Color.White).padding(12.dp).align(Alignment.CenterStart)
                ) { Text("Dale, nos vemos ahí") }
            }
        }
    }
}