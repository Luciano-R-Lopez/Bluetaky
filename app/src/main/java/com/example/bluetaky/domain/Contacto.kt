package com.example.bluetaky.domain

/**
 * Modelo de dominio: un dispositivo o persona que aparece en la lista de conectados.
 */
data class Contacto(
    val nombre: String,
    val conectado: Boolean
)
