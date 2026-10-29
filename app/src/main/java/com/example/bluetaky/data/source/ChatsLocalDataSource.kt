package com.example.bluetaky.data.source

import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fuente de datos en memoria de los chats: la lista de conversaciones y
 * los mensajes de la conversación abierta.
 *
 * Hoy todos los chats abren la misma conversación (con [INTERLOCUTOR]);
 * es una decisión de la app actual, no de esta capa.
 */
class ChatsLocalDataSource(
    chatsIniciales: List<Chat> = CHATS_INICIALES,
    conversacionInicial: List<Mensaje> = CONVERSACION_INICIAL
) {
    private val _chats = MutableStateFlow(chatsIniciales)
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _conversacion = MutableStateFlow(conversacionInicial)
    val conversacion: StateFlow<List<Mensaje>> = _conversacion.asStateFlow()

    fun agregarMensaje(mensaje: Mensaje) {
        _conversacion.update { it + mensaje }
    }

    companion object {
        const val INTERLOCUTOR = "Fabian Fernando"

        val CHATS_INICIALES = listOf(
            Chat("Fabian Fernando", "Todo bien amigo? sigue en pie lo de la juntada?"),
            Chat("Luciano", "Dame todo lo que me debes"),
            Chat("Fernando2", "viste que hay alguien que se llama como yo?"),
            Chat("lagarto1234", "grabamos un video?"),
            Chat("Javier Milei", "ahi te pague todo"),
            Chat("Alberzo Fernande", "Decime algo lindo")
        )

        val CONVERSACION_INICIAL = listOf(
            Mensaje("Fabian Fernando", "Todo bien amigo? sigue en pie lo de la juntada?", false),
            Mensaje("Yo", "Si, ahi voy a estar", true),
            Mensaje("Fabian Fernando", "Dale, nos vemos ahi", false)
        )
    }
}
