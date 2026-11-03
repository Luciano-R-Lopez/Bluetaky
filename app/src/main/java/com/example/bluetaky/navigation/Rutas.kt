package com.example.bluetaky.navigation

/**
 * Nombres de las pantallas. Los ViewModels no los conocen:
 * navegar es responsabilidad del grafo, no de la lógica de presentación.
 */
object Rutas {
    const val SPLASH = "splash"
    const val PERFIL = "perfil"
    const val CONECTADOS = "conectados"
    const val CHATS = "chats"
    const val COMUNIDAD = "comunidad"
    const val OPCIONES = "opciones"
    const val ARG_CHAT_ID = "chatId"
    const val CHAT_DETALLE = "chat_detalle/{$ARG_CHAT_ID}"

    /** Ruta concreta de un chat: reemplaza {chatId} por el id real. */
    fun chatDetalle(chatId: Int): String = "chat_detalle/$chatId"
}
