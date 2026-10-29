package com.example.bluetaky.presentation.opciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.AjustesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Estado de la pantalla de opciones.
 * No dibuja la pantalla, no conoce Navigation y no sabe de dónde salen los ajustes.
 */
class OpcionesViewModel(
    repository: AjustesRepository
) : ViewModel() {

    val uiState: StateFlow<OpcionesUiState> = repository.observarAjustes()
        .map { OpcionesUiState(ajustes = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = OpcionesUiState(ajustes = repository.observarAjustes().value)
        )
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
