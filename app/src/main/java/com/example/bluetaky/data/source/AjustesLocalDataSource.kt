package com.example.bluetaky.data.source

import com.example.bluetaky.domain.Ajuste
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Fuente de datos en memoria de las opciones de la app.
 * Hoy son solo de lectura; guardarlas de verdad sería un cambio de esta capa.
 */
class AjustesLocalDataSource(
    ajustesIniciales: List<Ajuste> = AJUSTES_INICIALES
) {
    private val _ajustes = MutableStateFlow(ajustesIniciales)
    val ajustes: StateFlow<List<Ajuste>> = _ajustes.asStateFlow()

    companion object {
        val AJUSTES_INICIALES = listOf(
            Ajuste("Cambiar a dark mode", "OFF"),
            Ajuste("Volumen", "50%"),
            Ajuste("Vibración", "SI"),
            Ajuste("Notificaciones", "SI")
        )
    }
}
