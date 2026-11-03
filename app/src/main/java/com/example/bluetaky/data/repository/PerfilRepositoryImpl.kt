package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.PerfilLocalDataSource
import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.Flow

/**
 * Implementación concreta de [PerfilRepository].
 *
 * Las funciones son suspend porque la fuente (DataStore) escribe en disco:
 * hay que llamarlas desde una coroutine. El contrato queda igual si mañana el perfil
 * sale de una base o de una API.
 */
class PerfilRepositoryImpl(
    private val origen: PerfilLocalDataSource
) : PerfilRepository {

    override fun observarPerfil(): Flow<Perfil> = origen.perfil

    override suspend fun actualizarNombre(nombre: String) {
        origen.actualizarNombre(nombre)
    }

    override suspend fun actualizarEstado(estado: String) {
        origen.actualizarEstado(estado)
    }
}
