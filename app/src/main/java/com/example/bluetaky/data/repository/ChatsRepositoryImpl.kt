package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.ChatsLocalDataSource
import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementación concreta de [ChatsRepository].
 * Los mensajes que envía el usuario salen firmados como "Yo", como en la app original.
 */
class ChatsRepositoryImpl(
    private val origen: ChatsLocalDataSource
) : ChatsRepository {

    override fun observarChats(): StateFlow<List<Chat>> = origen.chats

    override fun observarConversacion(): StateFlow<List<Mensaje>> = origen.conversacion

    override fun nombreInterlocutor(): String = ChatsLocalDataSource.INTERLOCUTOR

    override suspend fun enviarMensaje(texto: String) {
        origen.agregarMensaje(Mensaje("Yo", texto, true))
    }
}
