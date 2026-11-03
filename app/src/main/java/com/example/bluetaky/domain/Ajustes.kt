package com.example.bluetaky.domain

/**
 * Modelo de dominio: las opciones de configuración de la app tal como se guardan
 * (valores reales, no texto). Cómo se muestran ("ON", "SI", "50%") lo decide la pantalla.
 *
 * [volumen] va de 0 a 100 en pasos de 10.
 */
data class Ajustes(
    val darkMode: Boolean = false,
    val volumen: Int = 50,
    val vibracion: Boolean = true,
    val notificaciones: Boolean = true
)
