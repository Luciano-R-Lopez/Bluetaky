package com.example.bluetaky.data.repository

import com.example.bluetaky.data.local.ChatDao
import com.example.bluetaky.data.local.ChatEntity
import com.example.bluetaky.data.local.MensajeDao
import com.example.bluetaky.data.local.toDomain
import com.example.bluetaky.data.local.toEntity
import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementación concreta de [ChatsRepository] sobre Room (guía de Room:
 * el Repository es quien habla con los DAO).
 * Los mensajes que envía el usuario salen firmados como "Yo", como en la app original.
 */
class ChatsRepositoryImpl(
    private val chatDao: ChatDao,
    private val mensajeDao: MensajeDao
) : ChatsRepository {

    override fun observarChats(): Flow<List<Chat>> =
        chatDao.observarChats().map { chats -> chats.map { it.toDomain() } }

    override fun observarConversacion(chatId: Int): Flow<List<Mensaje>> =
        mensajeDao.observarMensajes(chatId).map { mensajes -> mensajes.map { it.toDomain() } }

    override fun observarNombreInterlocutor(chatId: Int): Flow<String> =
        chatDao.observarNombre(chatId).map { it ?: "" }

    override suspend fun enviarMensaje(chatId: Int, texto: String) {
        mensajeDao.insertar(Mensaje("Yo", texto, true).toEntity(chatId))
        chatDao.actualizarUltimoMensaje(chatId, texto)
    }

    /**
     * Es suspend y se resuelve acá (y no con un RoomDatabase.Callback) para que
     * sea una función común del repositorio, fácil de leer y de explicar.
     * Cada chat arranca con su vista previa como primer mensaje recibido; el de
     * Fabian Fernando conserva la conversación completa que tenía la app original.
     */
    override suspend fun cargarDatosIniciales() {
        if (chatDao.contar() > 0) {
            return
        }
        CHATS_INICIALES.forEach { (nombre, ultimoMensaje) ->
            val chatId = chatDao.insertar(ChatEntity(nombre = nombre, ultimoMensaje = ultimoMensaje)).toInt()
            val conversacion = if (nombre == INTERLOCUTOR_CON_HISTORIAL) {
                CONVERSACION_INICIAL
            } else {
                listOf(Mensaje(nombre, ultimoMensaje, false))
            }
            mensajeDao.insertarTodos(conversacion.map { it.toEntity(chatId) })
        }
    }

    companion object {
        private const val INTERLOCUTOR_CON_HISTORIAL = "Fabian Fernando"

        private val CHATS_INICIALES = listOf(
            "Fabian Fernando" to "Todo bien amigo? sigue en pie lo de la juntada?",
            "Luciano" to "Dame todo lo que me debes",
            "Fernando2" to "viste que hay alguien que se llama como yo?",
            "lagarto1234" to "grabamos un video?",
            "Javier Milei" to "ahi te pague todo",
            "Alberzo Fernande" to "Decime algo lindo"
        )

        private val CONVERSACION_INICIAL = listOf(
            Mensaje("Fabian Fernando", "Todo bien amigo? sigue en pie lo de la juntada?", false),
            Mensaje("Yo", "Si, ahi voy a estar", true),
            Mensaje("Fabian Fernando", "Dale, nos vemos ahi", false)
        )
    }
}
