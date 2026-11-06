package com.example.bluetaky.presentation.opciones

import com.example.bluetaky.domain.Ajustes

/**
 * Lo que muestra la pantalla de opciones: los ajustes guardados.
 * La pantalla decide cómo escribirlos (ON/OFF, SI/NO, porcentaje).
 *
 * [permisoNotificacionesDenegado] es true cuando el usuario rechazó el permiso de
 * notificaciones: la pantalla muestra entonces el aviso que explica cómo habilitarlo.
 */
data class OpcionesUiState(
    val ajustes: Ajustes = Ajustes(),
    val permisoNotificacionesDenegado: Boolean = false
)
