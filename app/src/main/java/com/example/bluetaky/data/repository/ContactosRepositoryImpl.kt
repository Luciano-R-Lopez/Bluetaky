package com.example.bluetaky.data.repository

import com.example.bluetaky.data.source.ContactosLocalDataSource
import com.example.bluetaky.domain.Contacto
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementación concreta de [ContactosRepository] sobre la fuente en memoria.
 */
class ContactosRepositoryImpl(
    private val origen: ContactosLocalDataSource
) : ContactosRepository {

    override fun observarContactos(): StateFlow<List<Contacto>> = origen.contactos
}
