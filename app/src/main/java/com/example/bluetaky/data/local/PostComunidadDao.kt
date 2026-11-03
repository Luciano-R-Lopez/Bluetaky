package com.example.bluetaky.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Acceso a la tabla "posts_comunidad".
 */
@Dao
interface PostComunidadDao {
    @Query("SELECT * FROM posts_comunidad ORDER BY id")
    fun observarPosts(): Flow<List<PostComunidadEntity>>

    @Query("SELECT COUNT(*) FROM posts_comunidad")
    suspend fun contar(): Int

    @Insert
    suspend fun insertar(post: PostComunidadEntity)

    @Insert
    suspend fun insertarTodos(posts: List<PostComunidadEntity>)
}
