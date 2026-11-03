package com.example.bluetaky.domain

/**
 * Modelo de dominio: una conversación tal como aparece en la lista de chats,
 * con el último mensaje como vista previa. El [id] identifica la conversación
 * para poder abrirla (va en la ruta) y buscar sus mensajes.
 */
data class Chat(
    val id: Int,
    val nombre: String,
    val ultimoMensaje: String
)
