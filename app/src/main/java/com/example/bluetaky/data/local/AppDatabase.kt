package com.example.bluetaky.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * La base de datos de la app (guía de Room). Se declara version = 1 y
 * exportSchema = false: no se guardan los esquemas en archivos.
 *
 * Es un singleton: abrir más de una instancia sobre el mismo archivo es costoso
 * y puede dar errores. @Volatile hace que todos los hilos vean enseguida la instancia
 * creada, y synchronized evita que dos hilos la creen a la vez.
 */
@Database(
    entities = [ChatEntity::class, MensajeEntity::class, PostComunidadEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun mensajeDao(): MensajeDao
    abstract fun postComunidadDao(): PostComunidadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bluetaky_db"
                ).build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}
