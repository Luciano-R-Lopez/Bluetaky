package com.example.bluetaky.data.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.bluetaky.data.PreferencesKeys
import com.example.bluetaky.data.dataStore
import com.example.bluetaky.domain.Ajustes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Fuente de datos de las opciones de la app: lee y escribe en DataStore.
 * Cada ajuste es una clave aparte; ?: da el valor inicial si todavía no se guardó.
 * Las escrituras son suspend: se llaman desde una coroutine (viewModelScope).
 */
class AjustesLocalDataSource(
    private val context: Context
) {
    val ajustes: Flow<Ajustes> = context.dataStore.data.map { prefs ->
        Ajustes(
            darkMode = prefs[PreferencesKeys.DARK_MODE] ?: false,
            volumen = prefs[PreferencesKeys.VOLUMEN] ?: 50,
            vibracion = prefs[PreferencesKeys.VIBRACION] ?: true,
            notificaciones = prefs[PreferencesKeys.NOTIFICACIONES] ?: true
        )
    }

    suspend fun actualizarDarkMode(activado: Boolean) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.DARK_MODE] = activado }
    }

    suspend fun actualizarVolumen(volumen: Int) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.VOLUMEN] = volumen }
    }

    suspend fun actualizarVibracion(activada: Boolean) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.VIBRACION] = activada }
    }

    suspend fun actualizarNotificaciones(activadas: Boolean) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.NOTIFICACIONES] = activadas }
    }
}
