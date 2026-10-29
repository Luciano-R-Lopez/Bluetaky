package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.StateFlow

/**
 * Lo único que el ViewModel necesita saber sobre los chats y la conversación abierta.
 */
interface ChatsRepository {
    fun observarChats(): StateFlow<List<Chat>>

    fun observarConversacion(): StateFlow<List<Mensaje>>

    /** Con quién es la conversación que se abre desde la lista. */
    fun nombreInterlocutor(): String

    suspend fun enviarMensaje(texto: String)
}