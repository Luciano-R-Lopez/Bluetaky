package com.example.bluetaky.presentation.opciones

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.core.content.ContextCompat
import com.example.bluetaky.domain.Ajustes
import com.example.bluetaky.ui.Colores
import com.example.bluetaky.ui.Paleta

/**
 * Observa el ViewModel y le reenvía cada cambio de ajuste. "Volver" se avisa hacia afuera:
 * el grafo hace popBackStack() y se vuelve a la pestaña desde la que se abrió Opciones.
 */
@Composable
fun OpcionesRoute(
    viewModel: OpcionesViewModel,
    paleta: Paleta,
    onVolver: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    OpcionesScreen(
        estado = estado,
        paleta = paleta,
        onAlternarDarkMode = viewModel::alternarDarkMode,
        onBajarVolumen = viewModel::bajarVolumen,
        onSubirVolumen = viewModel::subirVolumen,
        onAlternarVibracion = viewModel::alternarVibracion,
        onDesactivarNotificaciones = viewModel::desactivarNotificaciones,
        onResultadoPermisoNotificaciones = viewModel::resultadoPermisoNotificaciones,
        onVolver = onVolver
    )
}

/**
 * Dibuja [estado] y nada más. Cada ajuste es un Button que avisa hacia afuera;
 * no calcula el valor nuevo. Lo único que hace acá es hablar con el sistema para el permiso
 * de notificaciones (ver [permisoNotificacionesConcedido]).
 */
@Composable
fun OpcionesScreen(
    paleta: Paleta,
    estado: OpcionesUiState,
    onAlternarDarkMode: () -> Unit,
    onBajarVolumen: () -> Unit,
    onSubirVolumen: () -> Unit,
    onAlternarVibracion: () -> Unit,
    onDesactivarNotificaciones: () -> Unit,
    onResultadoPermisoNotificaciones: (Boolean) -> Unit,
    onVolver: () -> Unit
) {
    // El permiso se pide acá y no en el ViewModel porque necesita Context y el launcher
    // de la Activity. El ViewModel solo recibe el resultado y decide qué guardar.
    val contexto = LocalContext.current
    val lanzadorPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido -> onResultadoPermisoNotificaciones(concedido) }

    Column(modifier = Modifier.fillMaxSize().background(paleta.superficie).padding(16.dp)) {
        Text("Opciones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = paleta.acento)

        FilaOpcion(paleta, "Cambiar a dark mode") {
            BotonOpcion(paleta, if (estado.ajustes.darkMode) "ON" else "OFF", onAlternarDarkMode)
        }

        FilaOpcion(paleta, "Volumen") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotonOpcion(paleta, "-", onBajarVolumen)
                Text(
                    "${estado.ajustes.volumen}%",
                    fontWeight = FontWeight.Bold,
                    color = paleta.acento,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                BotonOpcion(paleta, "+", onSubirVolumen)
            }
        }

        FilaOpcion(paleta, "Vibración") {
            BotonOpcion(paleta, if (estado.ajustes.vibracion) "SI" else "NO", onAlternarVibracion)
        }

        FilaOpcion(paleta, "Notificaciones") {
            BotonOpcion(paleta, if (estado.ajustes.notificaciones) "SI" else "NO") {
                if (estado.ajustes.notificaciones) {
                    onDesactivarNotificaciones()
                } else if (permisoNotificacionesConcedido(contexto)) {
                    onResultadoPermisoNotificaciones(true)
                } else {
                    lanzadorPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        if (estado.permisoNotificacionesDenegado) {
            Text(
                "Bluetaky necesita este permiso para avisarte de mensajes nuevos. " +
                    "Podés habilitarlo desde Configuración del sistema > Aplicaciones > Bluetaky > Notificaciones.",
                color = paleta.error,
                fontSize = 13.sp
            )
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = paleta.principal,
                contentColor = paleta.sobrePrincipal
            )
        ) { Text("Volver") }
    }
}

@Composable
private fun FilaOpcion(paleta: Paleta, etiqueta: String, control: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(paleta.fondo)
            .padding(12.dp)
    ) {
        Text(etiqueta, color = paleta.texto, modifier = Modifier.align(Alignment.CenterStart))
        Box(modifier = Modifier.align(Alignment.CenterEnd)) { control() }
    }
}

@Composable
private fun BotonOpcion(paleta: Paleta, texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = paleta.principal,
            contentColor = paleta.sobrePrincipal
        )
    ) { Text(texto) }
}

@Preview(showBackground = true)
@Composable
private fun OpcionesPreview() {
    OpcionesScreen(
        paleta = Colores.Clara,
        estado = OpcionesUiState(ajustes = Ajustes(volumen = 50, vibracion = true)),
        onAlternarDarkMode = {},
        onBajarVolumen = {},
        onSubirVolumen = {},
        onAlternarVibracion = {},
        onDesactivarNotificaciones = {},
        onResultadoPermisoNotificaciones = {},
        onVolver = {}
    )
}

/**
 * Los tres estados del permiso (apunte de permisos): concedido, denegado y no solicitado.
 * checkSelfPermission solo distingue concedido de no concedido; "denegado" vs "no solicitado"
 * lo sabemos por el resultado del launcher, que llega al ViewModel.
 *
 * POST_NOTIFICATIONS es un permiso peligroso (afecta lo que el usuario ve en su pantalla)
 * y desde Android 13 (API 33) hay que pedirlo en tiempo de ejecución, además de declararlo
 * en el Manifest. Antes de la API 33 no existe ese permiso: queda concedido al declararlo.
 * Aun pidiéndolo, la decisión final es del usuario y puede cambiarla desde Configuración.
 */
private fun permisoNotificacionesConcedido(contexto: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        return true
    }
    return ContextCompat.checkSelfPermission(
        contexto,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
}
