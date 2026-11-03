package com.example.bluetaky.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

/**
 * Se declara a nivel de archivo, fuera de cualquier Composable ni clase (clase de
 * persistencia, DataStore): si se creara más de una vez, habría varias instancias
 * apuntando al mismo archivo y fallaría en ejecución.
 *
 * Guarda datos simples de clave-valor: el perfil y los ajustes.
 * Los datos estructurados que crecen (chats y mensajes) van en Room.
 */
val Context.dataStore by preferencesDataStore(name = "bluetaky_prefs")

/**
 * Todas las claves en un solo lugar. El texto entre comillas es lo que queda
 * guardado en disco; el nombre de la constante es el que se usa desde Kotlin.
 */
object PreferencesKeys {
    val PERFIL_NOMBRE = stringPreferencesKey("perfil_nombre")
    val PERFIL_ESTADO = stringPreferencesKey("perfil_estado")
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val VOLUMEN = intPreferencesKey("volumen")
    val VIBRACION = booleanPreferencesKey("vibracion")
    val NOTIFICACIONES = booleanPreferencesKey("notificaciones")
}
