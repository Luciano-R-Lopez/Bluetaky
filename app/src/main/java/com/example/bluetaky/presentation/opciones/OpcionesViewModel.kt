package com.example.bluetaky.presentation.opciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.AjustesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado y eventos de la pantalla de opciones.
 * No dibuja la pantalla, no conoce Navigation y no sabe dónde se guardan los ajustes.
 *
 * Cada evento calcula el valor nuevo a partir del estado actual y lo manda al
 * repositorio dentro de viewModelScope.launch (las escrituras son suspend).
 * La pantalla se actualiza sola porque el Flow del repositorio vuelve a emitir.
 */
class OpcionesViewModel(
    private val repository: AjustesRepository
) : ViewModel() {

    val uiState: StateFlow<OpcionesUiState> = repository.observarAjustes()
        .map { OpcionesUiState(ajustes = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = OpcionesUiState()
        )

    fun alternarDarkMode() {
        val nuevo = !uiState.value.ajustes.darkMode
        viewModelScope.launch { repository.actualizarDarkMode(nuevo) }
    }

    fun alternarVibracion() {
        val nuevo = !uiState.value.ajustes.vibracion
        viewModelScope.launch { repository.actualizarVibracion(nuevo) }
    }

    fun alternarNotificaciones() {
        val nuevo = !uiState.value.ajustes.notificaciones
        viewModelScope.launch { repository.actualizarNotificaciones(nuevo) }
    }

    fun subirVolumen() {
        cambiarVolumen(uiState.value.ajustes.volumen + PASO_VOLUMEN)
    }

    fun bajarVolumen() {
        cambiarVolumen(uiState.value.ajustes.volumen - PASO_VOLUMEN)
    }

    /** El volumen nunca sale del rango 0..100. */
    private fun cambiarVolumen(valor: Int) {
        val limitado = valor.coerceIn(0, 100)
        viewModelScope.launch { repository.actualizarVolumen(limitado) }
    }

    companion object {
        const val PASO_VOLUMEN = 10
    }
}

class OpcionesViewModelFactory(
    private val repository: AjustesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpcionesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OpcionesViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
