package com.example.bluetaky.domain

/**
 * Modelo de dominio: una conversación tal como aparece en la lista de chats,
 * con el último mensaje como vista previa.
 */
data class Chat(
    val nombre: String,
    val ultimoMensaje: String
)
