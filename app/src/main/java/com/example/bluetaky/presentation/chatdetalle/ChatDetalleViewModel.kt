package com.example.bluetaky.presentation.chatdetalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bluetaky.data.repository.ChatsRepository
import com.example.bluetaky.presentation.puedeEnviarMensaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado y eventos de la conversación [chatId].
 * No dibuja la pantalla, no conoce Navigation y no sabe cómo se guardan los mensajes.
 * "Volver" no pasa por acá: la Route lo avisa hacia afuera y el grafo hace popBackStack().
 *
 * El chatId lo recibe por constructor (desde la Factory): así cada chat muestra
 * y guarda solo sus propios mensajes.
 */
class ChatDetalleViewModel(
    private val chatId: Int,
    private val repository: ChatsRepository
) : ViewModel() {

    private val texto = MutableStateFlow("")

    val uiState: StateFlow<ChatDetalleUiState> = combine(
        repository.observarNombreInterlocutor(chatId),
        repository.observarConversacion(chatId),
        texto
    ) { nombre, mensajes, escrito ->
        ChatDetalleUiState(
            nombreInterlocutor = nombre,
            texto = escrito,
            mensajes = mensajes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatDetalleUiState()
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
        viewModelScope.launch {
            repository.enviarMensaje(chatId, escrito)
        }
    }
}

class ChatDetalleViewModelFactory(
    private val chatId: Int,
    private val repository: ChatsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatDetalleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatDetalleViewModel(chatId, repository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}
