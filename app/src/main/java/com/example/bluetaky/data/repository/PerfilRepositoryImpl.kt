package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.PerfilLocalDataSource
import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementación concreta de [PerfilRepository].
 *
 * Las funciones son suspend aunque hoy la fuente responde al instante:
 * el contrato queda igual si mañana el perfil sale de una base o de una API.
 */
class PerfilRepositoryImpl(
    private val origen: PerfilLocalDataSource
) : PerfilRepository {

    override fun observarPerfil(): StateFlow<Perfil> = origen.perfil

    override suspend fun actualizarNombre(nombre: String) {
        origen.actualizarNombre(nombre)
    }

    override suspend fun actualizarEstado(estado: String) {
        origen.actualizarEstado(estado)
    }
}
