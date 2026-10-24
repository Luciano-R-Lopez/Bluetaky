package com.example.bluetaky.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(onAceptar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar: Box de tamaño fijo con fondo gris
        Box(
            modifier = Modifier.padding(top = 24.dp).size(100.dp).background(CampoGris),
            contentAlignment = Alignment.Center
        ) { Text("bt", color = AzulPrincipal, fontSize = 36.sp) }

        // TextField: lo que escribe el usuario se guarda en Sesion
        // y el encabezado de las otras pantallas se actualiza solo.
        TextField(
            value = Sesion.nombre,
            onValueChange = { Sesion.nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        )

        TextField(
            value = Sesion.estado,
            onValueChange = { Sesion.estado = it },
            label = { Text("Estado (opcional)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )

        Text(
            "Al usar esta aplicación aceptás nuestras políticas y condiciones de uso.",
            color = AzulPrincipal,
            fontSize = 13.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Button(
            onClick = onAceptar,
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal,
                contentColor = Color.White
            )
        ) { Text("Aceptar") }
    }
}
