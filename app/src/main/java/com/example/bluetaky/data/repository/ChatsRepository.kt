package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.Flow

/**
 * Lo único que el ViewModel necesita saber sobre los chats y las conversaciones.
 * Devuelve Flow (no StateFlow): el valor inicial lo pone el ViewModel con stateIn.
 */
interface ChatsRepository {
    fun observarChats(): Flow<List<Chat>>

    fun observarConversacion(chatId: Int): Flow<List<Mensaje>>

    /** Con quién es la conversación [chatId]. */
    fun observarNombreInterlocutor(chatId: Int): Flow<String>

    suspend fun enviarMensaje(chatId: Int, texto: String)

    /** Si todavía no hay chats guardados, carga los de ejemplo. Si ya hay, no hace nada. */
    suspend fun cargarDatosIniciales()
}
