package com.example.bluetaky.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Acceso a la tabla "mensajes". Cada conversación lee solo los suyos (WHERE chatId).
 */
@Dao
interface MensajeDao {
    @Query("SELECT * FROM mensajes WHERE chatId = :chatId ORDER BY id")
    fun observarMensajes(chatId: Int): Flow<List<MensajeEntity>>

    @Insert
    suspend fun insertar(mensaje: MensajeEntity)

    @Insert
    suspend fun insertarTodos(mensajes: List<MensajeEntity>)
}
