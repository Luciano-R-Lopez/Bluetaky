package com.example.bluetaky

import com.example.bluetaky.data.repository.AjustesRepository
import com.example.bluetaky.data.repository.ChatsRepository
import com.example.bluetaky.data.repository.PerfilRepository
import com.example.bluetaky.domain.Ajustes
import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje
import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repositorios falsos para probar los ViewModels sin Room, sin DataStore y sin Context:
 * guardan todo en un MutableStateFlow, que es lo único que los ViewModels necesitan.
 */
class PerfilRepositoryFalso(
    perfil: Perfil = Perfil("German Lantaño", "Ya fue wacho")
) : PerfilRepository {
    private val datos = MutableStateFlow(perfil)

    override fun observarPerfil(): Flow<Perfil> = datos

    override suspend fun actualizarNombre(nombre: String) {
        datos.update { it.copy(nombre = nombre) }
    }

    override suspend fun actualizarEstado(estado: String) {
        datos.update { it.copy(estado = estado) }
    }
}

class AjustesRepositoryFalso(
    ajustes: Ajustes = Ajustes()
) : AjustesRepository {
    private val datos = MutableStateFlow(ajustes)

    override fun observarAjustes(): Flow<Ajustes> = datos

    override suspend fun actualizarDarkMode(activado: Boolean) {
        datos.update { it.copy(darkMode = activado) }
    }

    override suspend fun actualizarVolumen(volumen: Int) {
        datos.update { it.copy(volumen = volumen) }
    }

    override suspend fun actualizarVibracion(activada: Boolean) {
        datos.update { it.copy(vibracion = activada) }
    }

    override suspend fun actualizarNotificaciones(activadas: Boolean) {
        datos.update { it.copy(notificaciones = activadas) }
    }
}

class ChatsRepositoryFalso : ChatsRepository {
    private val chats = MutableStateFlow(listOf(Chat(1, "Luciano", "hola")))
    private val mensajes = MutableStateFlow<List<Pair<Int, Mensaje>>>(emptyList())

    /** Cuántos mensajes se guardaron en total, para comprobar que no se envió nada. */
    val cantidadEnviados: Int get() = mensajes.value.size

    override fun observarChats(): Flow<List<Chat>> = chats

    override fun observarConversacion(chatId: Int): Flow<List<Mensaje>> =
        MutableStateFlow(mensajes.value.filter { it.first == chatId }.map { it.second })

    override fun observarNombreInterlocutor(chatId: Int): Flow<String> = MutableStateFlow("Luciano")

    override suspend fun enviarMensaje(chatId: Int, texto: String) {
        mensajes.update { it + (chatId to Mensaje("Yo", texto, true)) }
    }

    override suspend fun cargarDatosIniciales() = Unit
}
