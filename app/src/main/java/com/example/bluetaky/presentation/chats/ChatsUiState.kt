package com.example.bluetaky.presentation.chats

import com.example.bluetaky.domain.Chat

/**
 * Fotografía de la lista de chats en un momento dado.
 * La View dibuja esto. No filtra por su cuenta.
 */
data class ChatsUiState(
    val nombreUsuario: String = "",
    val busqueda: String = "",
    val chats: List<Chat> = emptyList()
)
