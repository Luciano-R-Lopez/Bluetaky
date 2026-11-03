package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.Flow

/**
 * Lo único que el ViewModel necesita saber sobre el perfil del usuario.
 * No dice dónde se guarda.
 */
interface PerfilRepository {
    fun observarPerfil(): Flow<Perfil>

    suspend fun actualizarNombre(nombre: String)

    suspend fun actualizarEstado(estado: String)
}
