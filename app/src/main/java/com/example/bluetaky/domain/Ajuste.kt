package com.example.bluetaky.domain

/**
 * Modelo de dominio: una opción de configuración con su valor ya formateado
 * para mostrar (por ejemplo "Volumen" -> "50%").
 */
data class Ajuste(
    val etiqueta: String,
    val valor: String
)
