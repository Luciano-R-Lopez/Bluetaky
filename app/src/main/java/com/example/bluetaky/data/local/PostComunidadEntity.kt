package com.example.bluetaky.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla "posts_comunidad": los mensajes del canal de la comunidad.
 * No guarda si el mensaje es propio: eso depende de quién es el usuario hoy
 * y lo decide el ViewModel comparando el autor con el nombre del perfil.
 */
@Entity(tableName = "posts_comunidad")
data class PostComunidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val autor: String,
    val texto: String
)
