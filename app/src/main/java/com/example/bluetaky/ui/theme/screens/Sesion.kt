package com.example.bluetaky.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Guarda los datos del perfil para que estén disponibles en todas las pantallas.
// Al ser mutableStateOf, cuando el usuario edita su nombre en Perfil
// el encabezado de las demás pantallas se vuelve a dibujar solo.
object Sesion {
    var nombre by mutableStateOf("German Lantaño")
    var estado by mutableStateOf("Ya fue wacho")
}
