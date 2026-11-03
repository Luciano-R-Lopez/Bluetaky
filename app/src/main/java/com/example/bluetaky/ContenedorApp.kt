package com.example.bluetaky

import android.content.Context
import com.example.bluetaky.data.local.AppDatabase
import com.example.bluetaky.data.repository.AjustesRepository
import com.example.bluetaky.data.repository.AjustesRepositoryImpl
import com.example.bluetaky.data.repository.ChatsRepository
import com.example.bluetaky.data.repository.ChatsRepositoryImpl
import com.example.bluetaky.data.repository.ComunidadRepository
import com.example.bluetaky.data.repository.ComunidadRepositoryImpl
import com.example.bluetaky.data.repository.ContactosRepository
import com.example.bluetaky.data.repository.ContactosRepositoryImpl
import com.example.bluetaky.data.repository.PerfilRepository
import com.example.bluetaky.data.repository.PerfilRepositoryImpl
import com.example.bluetaky.data.source.AjustesLocalDataSource
import com.example.bluetaky.data.source.ContactosLocalDataSource
import com.example.bluetaky.data.source.PerfilLocalDataSource

/**
 * Arma el Model una sola vez para toda la aplicación.
 *
 * Navigation crea cada ViewModel cuando se abre su pantalla, no todos juntos
 * en la Activity. Si los repositorios se crearan en onCreate o dentro de cada
 * ViewModel, al rotar o cambiar de pestaña las pantallas podrían quedar mirando
 * copias distintas de los datos (por ejemplo, el nombre del perfil que se edita
 * en una pantalla no llegaría al encabezado de las otras).
 */
object ContenedorApp {
    private lateinit var contexto: Context
    private lateinit var baseDeDatos: AppDatabase

    /**
     * Se llama una sola vez desde MainActivity.onCreate, antes de setContent
     * (igual que el ejemplo de clase crea el repositorio en MainActivity).
     * Se guarda applicationContext y no la Activity: el contexto de la Activity
     * se destruye al rotar y quedaría una referencia colgando.
     */
    fun inicializar(context: Context) {
        contexto = context.applicationContext
        baseDeDatos = AppDatabase.obtenerInstancia(context)
    }

    val repositorioPerfil: PerfilRepository by lazy {
        PerfilRepositoryImpl(PerfilLocalDataSource(contexto))
    }

    // Contactos sigue en memoria: depende de hardware y no entra en esta instancia.
    val repositorioContactos: ContactosRepository by lazy {
        ContactosRepositoryImpl(ContactosLocalDataSource())
    }

    val repositorioChats: ChatsRepository by lazy {
        ChatsRepositoryImpl(baseDeDatos.chatDao(), baseDeDatos.mensajeDao())
    }

    val repositorioComunidad: ComunidadRepository by lazy {
        ComunidadRepositoryImpl(baseDeDatos.postComunidadDao())
    }

    val repositorioAjustes: AjustesRepository by lazy {
        AjustesRepositoryImpl(AjustesLocalDataSource(contexto))
    }
}
