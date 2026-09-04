package com.example.bluetaky.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetaky.ui.theme.screens.*

@Composable
fun AppNavigation() {
    // NavController: controla a qué ruta se mueve la app
    val navController = rememberNavController()

    // NavHost: asocia cada ruta (String) con su pantalla
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(onComenzar = { navController.navigate("perfil") })
        }

        composable("perfil") {
            PerfilScreen(onAceptar = { navController.navigate("conectados") })
        }

        composable("conectados") {
            ConectadosScreen(
                onOpciones = { navController.navigate("opciones") },
                onIrAChats = {
                    navController.popBackStack()    // saca "conectados" de la pila
                    navController.navigate("chats") // y entra a "chats" sin acumularla
                },
                onIrAComunidad = {
                    navController.popBackStack()
                    navController.navigate("comunidad")
                }
            )
        }

        composable("chats") {
            ChatsScreen(
                onOpciones = { navController.navigate("opciones") },
                onIrAConectados = {
                    navController.popBackStack()
                    navController.navigate("conectados")
                },
                onIrAComunidad = {
                    navController.popBackStack()
                    navController.navigate("comunidad")
                },
                onAbrirChat = { navController.navigate("chat_detalle") }
            )
        }

        composable("comunidad") {
            ComunidadScreen(
                onOpciones = { navController.navigate("opciones") },
                onIrAConectados = {
                    navController.popBackStack()
                    navController.navigate("conectados")
                },
                onIrAChats = {
                    navController.popBackStack()
                    navController.navigate("chats")
                }
            )
        }

        composable("opciones") {
            // popBackStack: vuelve a la pestaña desde la que se abrió Opciones
            OpcionesScreen(onVolver = { navController.popBackStack() })
        }

        composable("chat_detalle") {
            ChatDetalleScreen(onVolver = { navController.popBackStack() })
        }
    }
}