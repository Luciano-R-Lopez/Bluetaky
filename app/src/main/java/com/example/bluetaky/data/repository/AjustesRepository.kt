package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Ajustes
import kotlinx.coroutines.flow.Flow

/**
 * Lo único que el ViewModel necesita saber sobre las opciones de la app.
 * Devuelve Flow (no StateFlow): el valor inicial lo pone el ViewModel con stateIn.
 */
interface AjustesRepository {
    fun observarAjustes(): Flow<Ajustes>

    suspend fun actualizarDarkMode(activado: Boolean)

    suspend fun actualizarVolumen(volumen: Int)

    suspend fun actualizarVibracion(activada: Boolean)

    suspend fun actualizarNotificaciones(activadas: Boolean)
}
