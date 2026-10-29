package com.example.bluetaky.presentation.comunidad

import com.example.bluetaky.domain.Mensaje

/**
 * Fotografía del canal de la comunidad: el encabezado, el mensaje que se
 * está escribiendo y los mensajes del canal.
 */
data class ComunidadUiState(
    val nombreUsuario: String = "",
    val texto: String = "",
    val mensajes: List<Mensaje> = emptyList()
)
