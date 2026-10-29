package com.example.bluetaky.data.source

import com.example.bluetaky.domain.Contacto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Fuente de datos en memoria de los contactos. Hoy la lista es fija,
 * pero se expone como StateFlow para que el contrato no cambie
 * el día que el estado en línea llegue de un escaneo Bluetooth real.
 */
class ContactosLocalDataSource(
    contactosIniciales: List<Contacto> = CONTACTOS_INICIALES
) {
    private val _contactos = MutableStateFlow(contactosIniciales)
    val contactos: StateFlow<List<Contacto>> = _contactos.asStateFlow()

    companion object {
        val CONTACTOS_INICIALES = listOf(
            Contacto("Alberzo Fernande", conectado = true),
            Contacto("lagarto1234", conectado = true),
            Contacto("Luciano", conectado = true),
            Contacto("Fernando2", conectado = false),
            Contacto("Fabian Fernando", conectado = false),
            Contacto("Javier Milei", conectado = false)
        )
    }
}
