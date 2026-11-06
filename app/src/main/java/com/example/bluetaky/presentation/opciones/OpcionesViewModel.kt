package com.example.bluetaky.presentation.opciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.AjustesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val permisoDenegado = MutableStateFlow(false)

    val uiState: StateFlow<OpcionesUiState> = combine(
        repository.observarAjustes(),
        permisoDenegado
    ) { ajustes, denegado ->
        OpcionesUiState(ajustes = ajustes, permisoNotificacionesDenegado = denegado)
    }.stateIn(
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

    /** Apagar las notificaciones no necesita ningún permiso. */
    fun desactivarNotificaciones() {
        permisoDenegado.value = false
        viewModelScope.launch { repository.actualizarNotificaciones(false) }
    }

    /**
     * Recibe el resultado del permiso POST_NOTIFICATIONS y decide qué se guarda.
     * La Screen solo pregunta al sistema y avisa acá lo que contestó el usuario.
     *
     * - Concedido: notificaciones = true en DataStore.
     * - Denegado: queda en false y se marca el estado para que la pantalla explique
     *   por qué la app lo necesita. La decisión final es del usuario: si denegó, la app
     *   no puede insistir y solo puede indicarle que lo habilite desde Configuración.
     */
    fun resultadoPermisoNotificaciones(concedido: Boolean) {
        permisoDenegado.value = !concedido
        viewModelScope.launch { repository.actualizarNotificaciones(concedido) }
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
