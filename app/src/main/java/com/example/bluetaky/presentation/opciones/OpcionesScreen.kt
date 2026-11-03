package com.example.bluetaky.presentation.opciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.bluetaky.domain.Ajustes
import com.example.bluetaky.ui.Colores

/**
 * Observa el ViewModel y le reenvía cada cambio de ajuste. "Volver" se avisa hacia afuera:
 * el grafo hace popBackStack() y se vuelve a la pestaña desde la que se abrió Opciones.
 */
@Composable
fun OpcionesRoute(
    viewModel: OpcionesViewModel,
    onVolver: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    OpcionesScreen(
        estado = estado,
        onAlternarDarkMode = viewModel::alternarDarkMode,
        onBajarVolumen = viewModel::bajarVolumen,
        onSubirVolumen = viewModel::subirVolumen,
        onAlternarVibracion = viewModel::alternarVibracion,
        onAlternarNotificaciones = viewModel::alternarNotificaciones,
        onVolver = onVolver
    )
}

/**
 * Dibuja [estado] y nada más. Cada ajuste es un Button que avisa hacia afuera;
 * no calcula el valor nuevo.
 */
@Composable
fun OpcionesScreen(
    estado: OpcionesUiState,
    onAlternarDarkMode: () -> Unit,
    onBajarVolumen: () -> Unit,
    onSubirVolumen: () -> Unit,
    onAlternarVibracion: () -> Unit,
    onAlternarNotificaciones: () -> Unit,
    onVolver: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text("Opciones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Colores.AzulPrincipal)

        FilaOpcion("Cambiar a dark mode") {
            BotonOpcion(if (estado.ajustes.darkMode) "ON" else "OFF", onAlternarDarkMode)
        }

        FilaOpcion("Volumen") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotonOpcion("-", onBajarVolumen)
                Text(
                    "${estado.ajustes.volumen}%",
                    fontWeight = FontWeight.Bold,
                    color = Colores.AzulPrincipal,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                BotonOpcion("+", onSubirVolumen)
            }
        }

        FilaOpcion("Vibración") {
            BotonOpcion(if (estado.ajustes.vibracion) "SI" else "NO", onAlternarVibracion)
        }

        FilaOpcion("Notificaciones") {
            BotonOpcion(if (estado.ajustes.notificaciones) "SI" else "NO", onAlternarNotificaciones)
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
private fun FilaOpcion(etiqueta: String, control: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Colores.FondoClaro)
            .padding(12.dp)
    ) {
        Text(etiqueta, modifier = Modifier.align(Alignment.CenterStart))
        Box(modifier = Modifier.align(Alignment.CenterEnd)) { control() }
    }
}

@Composable
private fun BotonOpcion(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Colores.AzulPrincipal,
            contentColor = Color.White
        )
    ) { Text(texto) }
}

@Preview(showBackground = true)
@Composable
private fun OpcionesPreview() {
    OpcionesScreen(
        estado = OpcionesUiState(ajustes = Ajustes(volumen = 50, vibracion = true)),
        onAlternarDarkMode = {},
        onBajarVolumen = {},
        onSubirVolumen = {},
        onAlternarVibracion = {},
        onAlternarNotificaciones = {},
        onVolver = {}
    )
}
