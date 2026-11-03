package com.example.bluetaky.data.local

import com.example.bluetaky.domain.Chat
import com.example.bluetaky.domain.Mensaje

/**
 * Pasa de entidades de Room a modelos de dominio y al revés.
 * Viven en data/ para que domain/ no importe nada de Room: el resto de la app
 * trabaja con Chat y Mensaje y no sabe cómo se guardan.
 */
fun ChatEntity.toDomain(): Chat = Chat(id = id, nombre = nombre, ultimoMensaje = ultimoMensaje)

fun MensajeEntity.toDomain(): Mensaje = Mensaje(autor = autor, texto = texto, propio = propio)

fun PostComunidadEntity.toDomain(): Mensaje = Mensaje(autor = autor, texto = texto, propio = propio)

fun Mensaje.toEntity(chatId: Int): MensajeEntity =
    MensajeEntity(chatId = chatId, autor = autor, texto = texto, propio = propio)

fun Mensaje.toPostEntity(): PostComunidadEntity = PostComunidadEntity(autor = autor, texto = texto, propio = propio)
