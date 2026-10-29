package com.example.bluetaky.presentation.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.ChatsRepository
import com.example.bluetaky.data.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * Estado y eventos de la pestaña Chats.
 * No dibuja la pantalla, no conoce Navigation y no sabe cómo se guardan los chats.
 * Abrir un chat no pasa por acá: la Route avisa hacia afuera y lo resuelve el grafo.
 */
class ChatsViewModel(
    chatsRepository: ChatsRepository,
    perfilRepository: PerfilRepository
) : ViewModel() {

    private val busqueda = MutableStateFlow("")

    val uiState: StateFlow<ChatsUiState> = combine(
        perfilRepository.observarPerfil(),
        chatsRepository.observarChats(),
        busqueda
    ) { perfil, chats, texto ->
        ChatsUiState(
            nombreUsuario = perfil.nombre,
            busqueda = texto,
            chats = filtrarChats(chats, texto)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatsUiState(
            nombreUsuario = perfilRepository.observarPerfil().value.nombre,
            busqueda = "",
            chats = chatsRepository.observarChats().value
        )
    )

    fun cambiarBusqueda(texto: String) {
        busqueda.value = texto
    }
}

class ChatsViewModelFactory(
    private val chatsRepository: ChatsRepository,
    private val perfilRepository: PerfilRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatsViewModel(chatsRepository, perfilRepository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
