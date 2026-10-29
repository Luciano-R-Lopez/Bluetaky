package com.example.bluetaky.presentation.chatdetalle

import com.example.bluetaky.domain.Mensaje

/**
 * Fotografía de la conversación abierta: con quién se habla,
 * el mensaje que se está escribiendo y los mensajes.
 */
data class ChatDetalleUiState(
    val nombreInterlocutor: String = "",
    val texto: String = "",
    val mensajes: List<Mensaje> = emptyList()
)
