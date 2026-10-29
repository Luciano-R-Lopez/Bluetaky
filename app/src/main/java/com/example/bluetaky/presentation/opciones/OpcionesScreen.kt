package com.example.bluetaky.presentation.opciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bluetaky.domain.Ajuste
import com.example.bluetaky.ui.Colores

/**
 * Observa el ViewModel. "Volver" se avisa hacia afuera: el grafo hace popBackStack()
 * y se vuelve a la pestaña desde la que se abrió Opciones.
 */
@Composable
fun OpcionesRoute(
    viewModel: OpcionesViewModel,
    onVolver: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    OpcionesScreen(
        estado = estado,
        onVolver = onVolver
    )
}

/**
 * Dibuja [estado] y nada más.
 */
@Composable
fun OpcionesScreen(
    estado: OpcionesUiState,
    onVolver: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text("Opciones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Colores.AzulPrincipal)

        estado.ajustes.forEach { ajuste ->
            FilaOpcion(ajuste.etiqueta, ajuste.valor)
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Colores.AzulPrincipal,
                contentColor = Color.White
            )
        ) { Text("Volver") }
    }
}

@Composable
private fun FilaOpcion(etiqueta: String, valor: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Colores.FondoClaro)
            .padding(12.dp)
    ) {
        Text(etiqueta, modifier = Modifier.align(Alignment.CenterStart))
        Text(
            valor,
            fontWeight = FontWeight.Bold,
            color = Colores.AzulPrincipal,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OpcionesPreview() {
    OpcionesScreen(
        estado = OpcionesUiState(
            ajustes = listOf(Ajuste("Volumen", "50%"), Ajuste("Vibración", "SI"))
        ),
        onVolver = {}
    )
}
