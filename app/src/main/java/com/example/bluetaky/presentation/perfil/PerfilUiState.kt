package com.example.bluetaky.presentation.perfil

/**
 * Lo que muestra el formulario de perfil: los dos campos editables.
 * No incluye "ir a Conectados": eso ocurre una sola vez y lo resuelve la navegación.
 */
data class PerfilUiState(
    val nombre: String = "",
    val estado: String = ""
)
