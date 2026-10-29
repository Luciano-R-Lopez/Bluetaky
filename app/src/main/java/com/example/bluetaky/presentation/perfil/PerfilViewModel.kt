package com.example.bluetaky.presentation.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Estado y eventos del perfil.
 * No dibuja la pantalla, no conoce Navigation y no sabe cómo se guarda el perfil.
 *
 * Cada letra escrita se guarda enseguida en el repositorio: así el encabezado
 * de las otras pantallas ya muestra el nombre nuevo cuando se abren.
 * El estado de los campos se actualiza primero acá y después en el repositorio
 * (en vez de derivarlo del repositorio) para que el cursor del TextField no
 * salte mientras el usuario escribe.
 */
class PerfilViewModel(
    private val repository: PerfilRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        repository.observarPerfil().value.let { perfil ->
            PerfilUiState(nombre = perfil.nombre, estado = perfil.estado)
        }
    )
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun cambiarNombre(valor: String) {
        _uiState.update { it.copy(nombre = valor) }
        viewModelScope.launch { repository.actualizarNombre(valor) }
    }

    fun cambiarEstado(valor: String) {
        _uiState.update { it.copy(estado = valor) }
        viewModelScope.launch { repository.actualizarEstado(valor) }
    }
}

class PerfilViewModelFactory(
    private val repository: PerfilRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
