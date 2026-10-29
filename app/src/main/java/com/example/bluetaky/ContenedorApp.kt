package com.example.bluetaky

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
import com.example.bluetaky.data.source.ChatsLocalDataSource
import com.example.bluetaky.data.source.ComunidadLocalDataSource
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
    val repositorioPerfil: PerfilRepository by lazy {
        PerfilRepositoryImpl(PerfilLocalDataSource())
    }

    val repositorioContactos: ContactosRepository by lazy {
        ContactosRepositoryImpl(ContactosLocalDataSource())
    }

    val repositorioChats: ChatsRepository by lazy {
        ChatsRepositoryImpl(ChatsLocalDataSource())
    }

    val repositorioComunidad: ComunidadRepository by lazy {
        ComunidadRepositoryImpl(ComunidadLocalDataSource())
    }

    val repositorioAjustes: AjustesRepository by lazy {
        AjustesRepositoryImpl(AjustesLocalDataSource())
    }
}
