package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.StateFlow

/**
 * Lo único que el ViewModel necesita saber sobre los mensajes de la comunidad.
 */
interface ComunidadRepository {
    fun observarMensajes(): StateFlow<List<Mensaje>>

    suspend fun enviar(autor: String, texto: String)
}
