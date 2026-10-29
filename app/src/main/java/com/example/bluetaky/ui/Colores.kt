package com.example.bluetaky.ui

import androidx.compose.ui.graphics.Color

/**
 * Paleta de la app. Se usa en lugar de MaterialTheme.colorScheme porque las
 * pantallas pintan con estos colores fijos (fondos, botones, burbujas).
 * Es capa visual: no sabe nada de datos ni de pantallas.
 */
object Colores {
    val AzulPrincipal = Color(0xFF3A6EA5)
    val AzulOscuro = Color(0xFF2C5680)
    val FondoClaro = Color(0xFFF2F2F2)
    val CampoGris = Color(0xFFE3E3E3)
}
