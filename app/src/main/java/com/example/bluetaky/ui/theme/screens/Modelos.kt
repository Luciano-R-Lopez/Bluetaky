package com.example.bluetaky.ui.theme.screens

// Un mensaje dentro de una conversación.
// propio = true  -> lo escribió el usuario, se alinea a la derecha
// propio = false -> lo recibió, se alinea a la izquierda
data class Mensaje(
    val autor: String,
    val texto: String,
    val propio: Boolean
)
