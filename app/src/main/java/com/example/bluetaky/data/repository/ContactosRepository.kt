package com.example.bluetaky.data.repository

import com.example.bluetaky.domain.Contacto
import kotlinx.coroutines.flow.StateFlow

/**
 * Lo único que el ViewModel necesita saber sobre los contactos.
 * Entrega todos; qué contactos se muestran lo decide el ViewModel.
 */
interface ContactosRepository {
    fun observarContactos(): StateFlow<List<Contacto>>
}
