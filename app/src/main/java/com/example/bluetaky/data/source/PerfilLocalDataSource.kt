package com.example.bluetaky.data.source

import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fuente de datos en memoria del perfil. Es la parte del Model que guarda
 * el nombre y el estado del usuario (lo que antes vivía en el object Sesion).
 * Quien observa [perfil] se entera cuando cambia.
 */
class PerfilLocalDataSource(
    perfilInicial: Perfil = PERFIL_INICIAL
) {
    private val _perfil = MutableStateFlow(perfilInicial)
    val perfil: StateFlow<Perfil> = _perfil.asStateFlow()

    fun actualizarNombre(nombre: String) {
        _perfil.update { it.copy(nombre = nombre) }
    }

    fun actualizarEstado(estado: String) {
        _perfil.update { it.copy(estado = estado) }
    }

    companion object {
        val PERFIL_INICIAL = Perfil(
            nombre = "German Lantaño",
            estado = "Ya fue wacho"
        )
    }
}
