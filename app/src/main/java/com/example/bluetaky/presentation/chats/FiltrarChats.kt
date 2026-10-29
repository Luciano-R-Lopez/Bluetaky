package com.example.bluetaky.presentation.chats

import com.example.bluetaky.domain.Chat

/**
 * Lógica de presentación pura: se puede probar sin Compose y sin ViewModel.
 * Filtra por nombre o por el contenido del último mensaje, sin distinguir mayúsculas.
 */
fun filtrarChats(chats: List<Chat>, busqueda: String): List<Chat> {
    return chats.filter { chat ->
        chat.nombre.contains(busqueda, ignoreCase = true) ||
            chat.ultimoMensaje.contains(busqueda, ignoreCase = true)
    }
}
