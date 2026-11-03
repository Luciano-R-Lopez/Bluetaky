package com.example.bluetaky.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla "mensajes": los mensajes de las conversaciones privadas.
 * [chatId] dice a qué chat pertenece cada uno; se filtra con WHERE chatId = :chatId.
 * Es un Int común, no una relación de Room: la unión la hace la consulta.
 */
@Entity(tableName = "mensajes")
data class MensajeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chatId: Int,
    val autor: String,
    val texto: String,
    val propio: Boolean
)
