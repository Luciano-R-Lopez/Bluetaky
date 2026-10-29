package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.AjustesLocalDataSource
import com.example.bluetaky.domain.Ajuste
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementación concreta de [AjustesRepository] sobre la fuente en memoria.
 */
class AjustesRepositoryImpl(
    private val origen: AjustesLocalDataSource
) : AjustesRepository {

    override fun observarAjustes(): StateFlow<List<Ajuste>> = origen.ajustes
}
