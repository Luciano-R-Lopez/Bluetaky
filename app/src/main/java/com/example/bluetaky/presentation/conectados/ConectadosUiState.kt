package com.example.bluetaky.presentation.conectados

import com.example.bluetaky.domain.Contacto

/**
 * Fotografía de la pantalla de conectados en un momento dado.
 * La View dibuja esto. No filtra ni separa conectados de desconectados por su cuenta.
 */
data class ConectadosUiState(
    val nombreUsuario: String = "",
    val busqueda: String = "",
    val conectados: List<Contacto> = emptyList(),
    val desconectados: List<Contacto> = emptyList()
) {
    /** Ya viene decidido: la View solo pregunta si debe mostrar el aviso. */
    val sinResultados: Boolean get() = conectados.isEmpty() && desconectados.isEmpty()
}
