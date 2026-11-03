package com.example.bluetaky.data.repository

import com.example.bluetaky.data.local.PostComunidadDao
import com.example.bluetaky.data.local.toDomain
import com.example.bluetaky.data.local.toPostEntity
import com.example.bluetaky.domain.Mensaje
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementación concreta de [ComunidadRepository] sobre Room.
 * Quién es el autor lo decide quien llama.
 */
class ComunidadRepositoryImpl(
    private val postDao: PostComunidadDao
) : ComunidadRepository {

    override fun observarMensajes(): Flow<List<Mensaje>> =
        postDao.observarPosts().map { posts -> posts.map { it.toDomain() } }

    override suspend fun enviar(autor: String, texto: String) {
        postDao.insertar(Mensaje(autor, texto, true).toPostEntity())
    }

    override suspend fun cargarDatosIniciales() {
        if (postDao.contar() > 0) {
            return
        }
        postDao.insertarTodos(MENSAJES_INICIALES.map { it.toPostEntity() })
    }

    companion object {
        private val MENSAJES_INICIALES = listOf(
            Mensaje("Alberzo Fernande", "sale joda en olivos, lleven bebida", false),
            Mensaje("lagarto1234", "llevo helado!", false),
            Mensaje("Luciano", "tengo limones y coca", false)
        )
    }
}
