package com.example.bluetaky.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla "chats" (guía de Room): una fila por conversación de la lista.
 * Con autoGenerate = true la base asigna el id; al insertar se deja en 0.
 */
@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ultimoMensaje: String
)
