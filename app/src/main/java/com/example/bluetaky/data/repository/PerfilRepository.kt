package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.StateFlow

/**
 * Lo único que el ViewModel necesita saber sobre el perfil del usuario.
 * No dice dónde se guarda.
 */
interface PerfilRepository {
    fun observarPerfil(): StateFlow<Perfil>

    suspend fun actualizarNombre(nombre: String)

    suspend fun actualizarEstado(estado: String)
}
