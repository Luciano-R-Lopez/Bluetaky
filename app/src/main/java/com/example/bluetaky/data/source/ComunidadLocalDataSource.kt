package com.example.bluetaky.data.source

import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fuente de datos en memoria de los mensajes del canal de la comunidad.
 */
class ComunidadLocalDataSource(
    mensajesIniciales: List<Mensaje> = MENSAJES_INICIALES
) {
    private val _mensajes = MutableStateFlow(mensajesIniciales)
    val mensajes: StateFlow<List<Mensaje>> = _mensajes.asStateFlow()

    fun agregar(mensaje: Mensaje) {
        _mensajes.update { it + mensaje }
    }

    companion object {
        val MENSAJES_INICIALES = listOf(
            Mensaje("Alberzo Fernande", "sale joda en olivos, lleven bebida", false),
            Mensaje("lagarto1234", "llevo helado!", false),
            Mensaje("Luciano", "tengo limones y coca", false)
        )
    }
}
