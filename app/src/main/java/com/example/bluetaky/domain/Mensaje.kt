package com.example.bluetaky.domain

/**
 * Modelo de dominio: un mensaje dentro de una conversación.
 * No es la burbuja que se dibuja ni el estado de ninguna pantalla.
 *
 * [propio] = true  -> lo escribió el usuario (se alinea a la derecha).
 * [propio] = false -> lo recibió (se alinea a la izquierda).
 */
data class Mensaje(
    val autor: String,
    val texto: String,
    val propio: Boolean
)
