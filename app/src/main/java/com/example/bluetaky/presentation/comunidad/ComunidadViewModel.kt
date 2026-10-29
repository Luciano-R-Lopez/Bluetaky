package com.example.bluetaky.presentation.comunidad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.ComunidadRepository
import com.example.bluetaky.data.repository.PerfilRepository
import com.example.bluetaky.presentation.puedeEnviarMensaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado y eventos de la pestaña Comunidad.
 * No dibuja la pantalla, no conoce Navigation y no sabe cómo se guardan los mensajes.
 *
 * El texto en escritura vive acá y no en la View para que sobreviva a la rotación.
 * Los mensajes enviados viven en el repositorio, por eso no se pierden al cambiar de pestaña.
 */
class ComunidadViewModel(
    private val comunidadRepository: ComunidadRepository,
    private val perfilRepository: PerfilRepository
) : ViewModel() {

    private val texto = MutableStateFlow("")

    val uiState: StateFlow<ComunidadUiState> = combine(
        perfilRepository.observarPerfil(),
        comunidadRepository.observarMensajes(),
        texto
    ) { perfil, mensajes, escrito ->
        ComunidadUiState(
            nombreUsuario = perfil.nombre,
            texto = escrito,
            mensajes = mensajes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ComunidadUiState(
            nombreUsuario = perfilRepository.observarPerfil().value.nombre,
            texto = "",
            mensajes = comunidadRepository.observarMensajes().value
        )
    )

    fun cambiarTexto(valor: String) {
        texto.value = valor
    }

    fun enviar() {
        val escrito = texto.value
        if (!puedeEnviarMensaje(escrito)) {
            return
        }
        texto.value = ""
        val autor = perfilRepository.observarPerfil().value.nombre
        viewModelScope.launch {
            comunidadRepository.enviar(autor, escrito)
        }
    }
}

class ComunidadViewModelFactory(
    private val comunidadRepository: ComunidadRepository,
    private val perfilRepository: PerfilRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComunidadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComunidadViewModel(comunidadRepository, perfilRepository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
