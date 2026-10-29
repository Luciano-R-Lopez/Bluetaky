package com.example.bluetaky.presentation.conectados

import com.example.bluetaky.domain.Contacto

/**
 * Lógica de presentación pura: se puede probar sin Compose y sin ViewModel.
 * Deja los contactos cuyo nombre contiene [busqueda], sin distinguir mayúsculas.
 */
fun filtrarContactos(contactos: List<Contacto>, busqueda: String): List<Contacto> {
    return contactos.filter { it.nombre.contains(busqueda, ignoreCase = true) }
}
