package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.AjustesLocalDataSource
import com.example.bluetaky.domain.Ajustes
import kotlinx.coroutines.flow.Flow

/**
 * Implementación concreta de [AjustesRepository] sobre la fuente que guarda en DataStore.
 */
class AjustesRepositoryImpl(
    private val origen: AjustesLocalDataSource
) : AjustesRepository {

    override fun observarAjustes(): Flow<Ajustes> = origen.ajustes

    override suspend fun actualizarDarkMode(activado: Boolean) {
        origen.actualizarDarkMode(activado)
    }

    override suspend fun actualizarVolumen(volumen: Int) {
        origen.actualizarVolumen(volumen)
    }

    override suspend fun actualizarVibracion(activada: Boolean) {
        origen.actualizarVibracion(activada)
    }

    override suspend fun actualizarNotificaciones(activadas: Boolean) {
        origen.actualizarNotificaciones(activadas)
    }
}
