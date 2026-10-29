package com.example.bluetaky.presentation.conectados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.ContactosRepository
import com.example.bluetaky.data.repository.PerfilRepository
import com.example.bluetaky.domain.Contacto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * Estado y eventos de la pestaña Conectados.
 * No dibuja la pantalla, no conoce Navigation y no sabe cómo se guardan los contactos.
 *
 * Al rotar, el ViewModel sigue vivo y conserva la búsqueda. Al cambiar de pestaña
 * se destruye (la entrada sale del back stack) y la búsqueda empieza vacía, como antes.
 */
class ConectadosViewModel(
    contactosRepository: ContactosRepository,
    perfilRepository: PerfilRepository
) : ViewModel() {

    private val busqueda = MutableStateFlow("")

    val uiState: StateFlow<ConectadosUiState> = combine(
        perfilRepository.observarPerfil(),
        contactosRepository.observarContactos(),
        busqueda
    ) { perfil, contactos, texto ->
        construirEstado(perfil.nombre, contactos, texto)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = construirEstado(
            nombre = perfilRepository.observarPerfil().value.nombre,
            contactos = contactosRepository.observarContactos().value,
            texto = ""
        )
    )

    fun cambiarBusqueda(texto: String) {
        busqueda.value = texto
    }

    private fun construirEstado(
        nombre: String,
        contactos: List<Contacto>,
        texto: String
    ): ConectadosUiState {
        val visibles = filtrarContactos(contactos, texto)
        return ConectadosUiState(
            nombreUsuario = nombre,
            busqueda = texto,
            conectados = visibles.filter { it.conectado },
            desconectados = visibles.filter { !it.conectado }
        )
    }
}

class ConectadosViewModelFactory(
    private val contactosRepository: ContactosRepository,
    private val perfilRepository: PerfilRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConectadosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConectadosViewModel(contactosRepository, perfilRepository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
