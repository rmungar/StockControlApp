package org.example.Services

import org.example.Entitites.Usuario
import org.example.Repositories.usuarioRepository

class usuarioService {

    companion object{
        private val usuarioRepository = usuarioRepository()
    }

    fun addUsuario(usuario: Usuario){
        return usuarioRepository.addUser(usuario)
    }


    fun getUsuario(nombreUsuario: String):Usuario? {
        return usuarioRepository.getUser(nombreUsuario)
    }


}