package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.ComunidadLocalDataSource
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementación concreta de [ComunidadRepository]. Todo lo que envía el usuario
 * es un mensaje propio; quién es el autor lo decide quien llama.
 */
class ComunidadRepositoryImpl(
    private val origen: ComunidadLocalDataSource
) : ComunidadRepository {

    override fun observarMensajes(): StateFlow<List<Mensaje>> = origen.mensajes

    override suspend fun enviar(autor: String, texto: String) {
        origen.agregar(Mensaje(autor, texto, true))
    }
}
