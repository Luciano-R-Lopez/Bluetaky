package com.example.bluetaky.presentation.opciones

import com.example.bluetaky.domain.Ajuste

/**
 * Lo que muestra la pantalla de opciones: la lista de ajustes ya lista para dibujar.
 */
data class OpcionesUiState(
    val ajustes: List<Ajuste> = emptyList()
)
