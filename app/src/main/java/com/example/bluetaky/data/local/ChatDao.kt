package com.example.bluetaky.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Acceso a la tabla "chats". Las lecturas devuelven Flow: Room vuelve a emitir
 * la lista cada vez que la tabla cambia. Las escrituras son suspend.
 */
@Dao
interface ChatDao {
    @Query("SELECT * FROM chats ORDER BY id")
    fun observarChats(): Flow<List<ChatEntity>>

    @Query("SELECT nombre FROM chats WHERE id = :chatId")
    fun observarNombre(chatId: Int): Flow<String?>

    @Query("SELECT COUNT(*) FROM chats")
    suspend fun contar(): Int

    /** Devuelve el id que la base le asignó al chat. */
    @Insert
    suspend fun insertar(chat: ChatEntity): Long

    @Query("UPDATE chats SET ultimoMensaje = :texto WHERE id = :chatId")
    suspend fun actualizarUltimoMensaje(chatId: Int, texto: String)
}
