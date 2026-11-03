package com.example.bluetaky

import com.example.bluetaky.presentation.chatdetalle.ChatDetalleViewModel
import com.example.bluetaky.presentation.opciones.OpcionesViewModel
import com.example.bluetaky.presentation.perfil.PerfilViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Prueba los ViewModels con repositorios falsos: sin Room, sin DataStore, sin Context.
 *
 * viewModelScope usa Dispatchers.Main, que no existe en una prueba de JVM:
 * se reemplaza por un dispatcher de prueba que ejecuta las coroutines al instante.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelsTest {

    @Before
    fun prepararMain() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun limpiarMain() {
        Dispatchers.resetMain()
    }

    @Test
    fun perfilViewModel_guardaElNombre() = runTest {
        val repositorio = PerfilRepositoryFalso()
        val viewModel = PerfilViewModel(repositorio)

        viewModel.cambiarNombre("Luciano")

        assertEquals("Luciano", viewModel.uiState.value.nombre)
        assertEquals("Luciano", repositorio.observarPerfil().first().nombre)
    }

    @Test
    fun opcionesViewModel_alternaUnAjuste() = runTest {
        val viewModel = OpcionesViewModel(AjustesRepositoryFalso())
        // WhileSubscribed: el estado solo avanza mientras alguien lo observa.
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }
        assertTrue(viewModel.uiState.value.ajustes.vibracion)

        viewModel.alternarVibracion()

        assertFalse(viewModel.uiState.value.ajustes.vibracion)
    }

    @Test
    fun opcionesViewModel_volumenSubeYBajaDeADiezSinPasarseDelRango() = runTest {
        val viewModel = OpcionesViewModel(AjustesRepositoryFalso())
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.subirVolumen()
        assertEquals(60, viewModel.uiState.value.ajustes.volumen)

        repeat(20) { viewModel.bajarVolumen() }
        assertEquals(0, viewModel.uiState.value.ajustes.volumen)
    }

    @Test
    fun chatDetalleViewModel_noEnviaMensajesVacios() = runTest {
        val repositorio = ChatsRepositoryFalso()
        val viewModel = ChatDetalleViewModel(1, repositorio)

        viewModel.cambiarTexto("   ")
        viewModel.enviar()

        assertEquals(0, repositorio.cantidadEnviados)
    }

    @Test
    fun chatDetalleViewModel_enviaUnMensajeConTexto() = runTest {
        val repositorio = ChatsRepositoryFalso()
        val viewModel = ChatDetalleViewModel(1, repositorio)

        viewModel.cambiarTexto("hola")
        viewModel.enviar()

        assertEquals(1, repositorio.cantidadEnviados)
    }
}
