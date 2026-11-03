package com.example.bluetaky.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla "posts_comunidad": los mensajes del canal de la comunidad.
 * [propio] guarda si lo escribió el usuario de este dispositivo, igual que en "mensajes":
 * así cada mensaje conserva su lado aunque después cambie el nombre del perfil.
 */
@Entity(tableName = "posts_comunidad")
data class PostComunidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val autor: String,
    val texto: String,
    val propio: Boolean
)
