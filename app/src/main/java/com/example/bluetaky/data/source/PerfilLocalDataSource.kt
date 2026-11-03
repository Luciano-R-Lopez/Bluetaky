package com.example.bluetaky.data.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.bluetaky.data.PreferencesKeys
import com.example.bluetaky.data.dataStore
import com.example.bluetaky.domain.Perfil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Fuente de datos del perfil: lee y escribe el nombre y el estado en DataStore.
 * Es el único lugar del perfil que conoce DataStore (como UserPreferencesRepository
 * en el ejemplo de clase). Quien observa [perfil] se entera cuando cambia.
 *
 * El operador ?: da el valor inicial si la clave todavía no fue guardada.
 */
class PerfilLocalDataSource(
    private val context: Context
) {
    val perfil: Flow<Perfil> = context.dataStore.data.map { prefs ->
        Perfil(
            nombre = prefs[PreferencesKeys.PERFIL_NOMBRE] ?: NOMBRE_INICIAL,
            estado = prefs[PreferencesKeys.PERFIL_ESTADO] ?: ESTADO_INICIAL
        )
    }

    suspend fun actualizarNombre(nombre: String) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.PERFIL_NOMBRE] = nombre }
    }

    suspend fun actualizarEstado(estado: String) {
        context.dataStore.edit { prefs -> prefs[PreferencesKeys.PERFIL_ESTADO] = estado }
    }

    companion object {
        const val NOMBRE_INICIAL = "German Lantaño"
        const val ESTADO_INICIAL = "Ya fue wacho"
    }
}
