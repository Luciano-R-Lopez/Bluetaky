package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.Flow

/**
 * Lo único que el ViewModel necesita saber sobre los mensajes de la comunidad.
 */
interface ComunidadRepository {
    fun observarMensajes(): Flow<List<Mensaje>>

    suspend fun enviar(autor: String, texto: String)

    /** Si todavía no hay mensajes guardados, carga los de ejemplo. Si ya hay, no hace nada. */
    suspend fun cargarDatosIniciales()
}
