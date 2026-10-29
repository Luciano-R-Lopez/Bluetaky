package com.example.bluetaky.presentation.perfil

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.ui.Colores

/**
 * Observa el ViewModel y le reenvía lo que el usuario escribe.
 * "Aceptar" se avisa hacia afuera para que el grafo de navegación elija la ruta.
 */
@Composable
fun PerfilRoute(
    viewModel: PerfilViewModel,
    onAceptar: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    PerfilScreen(
        estado = estado,
        onNombre = viewModel::cambiarNombre,
        onEstado = viewModel::cambiarEstado,
        onAceptar = onAceptar
    )
}

/**
 * Dibuja [estado] y nada más.
 */
@Composable
fun PerfilScreen(
    estado: PerfilUiState,
    onNombre: (String) -> Unit,
    onEstado: (String) -> Unit,
    onAceptar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(top = 24.dp).size(100.dp).background(Colores.CampoGris),
            contentAlignment = Alignment.Center
        ) { Text("bt", color = Colores.AzulPrincipal, fontSize = 36.sp) }

        TextField(
            value = estado.nombre,
            onValueChange = onNombre,
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        )

        TextField(
            value = estado.estado,
            onValueChange = onEstado,
            label = { Text("Estado (opcional)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )

        Text(
            "Al usar esta aplicación aceptás nuestras políticas y condiciones de uso.",
            color = Colores.AzulPrincipal,
            fontSize = 13.sp,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Button(
            onClick = onAceptar,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colores.AzulPrincipal,
                contentColor = Color.White
            )
        ) { Text("Aceptar") }
    }
}

@Preview(showBackground = true)
@Composable
private fun PerfilPreview() {
    PerfilScreen(
        estado = PerfilUiState(nombre = "German Lantaño", estado = "Ya fue wacho"),
        onNombre = {},
        onEstado = {},
        onAceptar = {}
    )
}
