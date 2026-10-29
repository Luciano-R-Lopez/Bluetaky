package com.example.bluetaky.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetaky.ContenedorApp
import com.example.bluetaky.presentation.chatdetalle.ChatDetalleRoute
import com.example.bluetaky.presentation.chatdetalle.ChatDetalleViewModel
import com.example.bluetaky.presentation.chatdetalle.ChatDetalleViewModelFactory
import com.example.bluetaky.presentation.chats.ChatsRoute
import com.example.bluetaky.presentation.chats.ChatsViewModel
import com.example.bluetaky.presentation.chats.ChatsViewModelFactory
import com.example.bluetaky.presentation.comunidad.ComunidadRoute
import com.example.bluetaky.presentation.comunidad.ComunidadViewModel
import com.example.bluetaky.presentation.comunidad.ComunidadViewModelFactory
import com.example.bluetaky.presentation.conectados.ConectadosRoute
import com.example.bluetaky.presentation.conectados.ConectadosViewModel
import com.example.bluetaky.presentation.conectados.ConectadosViewModelFactory
import com.example.bluetaky.presentation.opciones.OpcionesRoute
import com.example.bluetaky.presentation.opciones.OpcionesViewModel
import com.example.bluetaky.presentation.opciones.OpcionesViewModelFactory
import com.example.bluetaky.presentation.perfil.PerfilRoute
import com.example.bluetaky.presentation.perfil.PerfilViewModel
import com.example.bluetaky.presentation.perfil.PerfilViewModelFactory
import com.example.bluetaky.presentation.splash.SplashScreen

/**
 * Conecta destinos. Traduce "el usuario quiere ir a X" a una ruta.
 * No filtra, no valida y no guarda: eso vive en cada ViewModel.
 *
 * viewModel() queda asociado a la entrada del back stack. Al rotar, se recupera
 * el mismo ViewModel. Al salir de la pantalla (popBackStack), se destruye.
 *
 * Los tabs Conectados/Chats/Comunidad son rutas separadas. Se cambia de uno a otro
 * con popBackStack() + navigate() para no acumular back stack entre tabs.
 */
@Composable
fun BluetakyNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {

        composable(Rutas.SPLASH) {
            // Sin ViewModel: la pantalla no tiene datos ni lógica, solo un botón.
            SplashScreen(
                onComenzar = { navController.navigate(Rutas.PERFIL) }
            )
        }

        composable(Rutas.PERFIL) {
            val factory = remember { PerfilViewModelFactory(ContenedorApp.repositorioPerfil) }
            val viewModel: PerfilViewModel = viewModel(factory = factory)

            PerfilRoute(
                viewModel = viewModel,
                onAceptar = { navController.navigate(Rutas.CONECTADOS) }
            )
        }

        composable(Rutas.CONECTADOS) {
            val factory = remember {
                ConectadosViewModelFactory(
                    ContenedorApp.repositorioContactos,
                    ContenedorApp.repositorioPerfil
                )
            }
            val viewModel: ConectadosViewModel = viewModel(factory = factory)

            ConectadosRoute(
                viewModel = viewModel,
                onOpciones = { navController.navigate(Rutas.OPCIONES) },
                onIrAChats = { navController.cambiarDeTab(Rutas.CHATS) },
                onIrAComunidad = { navController.cambiarDeTab(Rutas.COMUNIDAD) }
            )
        }

        composable(Rutas.CHATS) {
            val factory = remember {
                ChatsViewModelFactory(
                    ContenedorApp.repositorioChats,
                    ContenedorApp.repositorioPerfil
                )
            }
            val viewModel: ChatsViewModel = viewModel(factory = factory)

            ChatsRoute(
                viewModel = viewModel,
                onOpciones = { navController.navigate(Rutas.OPCIONES) },
                onIrAConectados = { navController.cambiarDeTab(Rutas.CONECTADOS) },
                onIrAComunidad = { navController.cambiarDeTab(Rutas.COMUNIDAD) },
                onAbrirChat = { navController.navigate(Rutas.CHAT_DETALLE) }
            )
        }

        composable(Rutas.COMUNIDAD) {
            val factory = remember {
                ComunidadViewModelFactory(
                    ContenedorApp.repositorioComunidad,
                    ContenedorApp.repositorioPerfil
                )
            }
            val viewModel: ComunidadViewModel = viewModel(factory = factory)

            ComunidadRoute(
                viewModel = viewModel,
                onOpciones = { navController.navigate(Rutas.OPCIONES) },
                onIrAConectados = { navController.cambiarDeTab(Rutas.CONECTADOS) },
                onIrAChats = { navController.cambiarDeTab(Rutas.CHATS) }
            )
        }

        composable(Rutas.OPCIONES) {
            val factory = remember { OpcionesViewModelFactory(ContenedorApp.repositorioAjustes) }
            val viewModel: OpcionesViewModel = viewModel(factory = factory)

            OpcionesRoute(
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.CHAT_DETALLE) {
            val factory = remember { ChatDetalleViewModelFactory(ContenedorApp.repositorioChats) }
            val viewModel: ChatDetalleViewModel = viewModel(factory = factory)

            ChatDetalleRoute(
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

/** Saca el tab actual de la pila y entra al nuevo, sin acumular entradas. */
private fun NavHostController.cambiarDeTab(ruta: String) {
    popBackStack()
    navigate(ruta)
}
