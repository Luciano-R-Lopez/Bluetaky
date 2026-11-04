package com.example.bluetaky.ui

import androidx.compose.ui.graphics.Color

/**
 * Los colores que usa una pantalla, con un nombre según para qué sirven
 * (no según de qué color son): así la misma pantalla se dibuja con la paleta
 * clara o con la oscura sin cambiar nada más.
 */
data class Paleta(
    val principal: Color,
    val principalOscuro: Color,
    val acento: Color,
    val tabActivo: Color,
    val textoTabActivo: Color,
    val fondo: Color,
    val campo: Color,
    val superficie: Color,
    val texto: Color,
    val textoSecundario: Color,
    val sobrePrincipal: Color,
    val error: Color
)

/**
 * Las dos paletas de la app. Se usa en lugar de MaterialTheme.colorScheme porque las
 * pantallas pintan con estos colores (fondos, botones, burbujas) y no con un tema.
 * La paleta activa la elige [paleta] según el dark mode guardado en DataStore, y el
 * grafo de navegación se la pasa a cada pantalla como parámetro.
 *
 * [principal] sirve de fondo (encabezado, botones, burbujas propias) con [sobrePrincipal]
 * encima. [acento] es el azul para usar como color de TEXTO sobre fondo o superficie:
 * en la paleta oscura es más claro para que se lea. [tabActivo] resalta la pestaña elegida.
 * Es capa visual: no sabe nada de datos ni de pantallas.
 */
object Colores {
    val Clara = Paleta(
        principal = Color(0xFF3A6EA5),
        principalOscuro = Color(0xFF2C5680),
        acento = Color(0xFF3A6EA5),
        tabActivo = Color.White,
        textoTabActivo = Color(0xFF3A6EA5),
        fondo = Color(0xFFF2F2F2),
        campo = Color(0xFFE3E3E3),
        superficie = Color.White,
        texto = Color.Black,
        textoSecundario = Color.DarkGray,
        sobrePrincipal = Color.White,
        error = Color.Red
    )

    val Oscura = Paleta(
        principal = Color(0xFF3F72AA),
        principalOscuro = Color(0xFF1B3A5C),
        acento = Color(0xFF7FB2E8),
        tabActivo = Color(0xFF3F72AA),
        textoTabActivo = Color.White,
        fondo = Color(0xFF121212),
        campo = Color(0xFF2A2A2A),
        superficie = Color(0xFF1E1E1E),
        texto = Color(0xFFEEEEEE),
        textoSecundario = Color(0xFFB0B0B0),
        sobrePrincipal = Color.White,
        error = Color(0xFFFF6B6B)
    )

    fun paleta(darkMode: Boolean): Paleta = if (darkMode) Oscura else Clara
}
