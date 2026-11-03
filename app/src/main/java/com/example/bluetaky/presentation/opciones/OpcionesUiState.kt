package com.example.bluetaky.presentation.opciones

import com.example.bluetaky.domain.Ajustes

/**
 * Lo que muestra la pantalla de opciones: los ajustes guardados.
 * La pantalla decide cómo escribirlos (ON/OFF, SI/NO, porcentaje).
 */
data class OpcionesUiState(
    val ajustes: Ajustes = Ajustes()
)
