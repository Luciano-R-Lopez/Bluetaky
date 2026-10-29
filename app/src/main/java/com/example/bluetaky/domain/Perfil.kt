package com.example.bluetaky.domain

/**
 * Modelo de dominio: los datos del usuario de la app.
 * Reemplaza al viejo object Sesion, que mezclaba datos con estado de Compose.
 */
data class Perfil(
    val nombre: String,
    val estado: String
)
