package com.example.bluetaky.presentation

/**
 * Regla compartida por Comunidad y Chat detalle: solo se envía si hay algo escrito.
 * Está aparte de los ViewModels para poder probarla sin Compose ni coroutines.
 */
fun puedeEnviarMensaje(texto: String): Boolean = texto.isNotBlank()
