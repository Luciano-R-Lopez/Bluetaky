package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Ajuste
import kotlinx.coroutines.flow.StateFlow

/**
 * Lo único que el ViewModel necesita saber sobre las opciones de la app.
 */
interface AjustesRepository {
    fun observarAjustes(): StateFlow<List<Ajuste>>
}
