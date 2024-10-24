package org.example.Repositories

import jakarta.persistence.Persistence
import org.example.Entitites.Usuario
import org.example.Utilities.Console
import kotlin.jvm.Throws

class usuarioRepository(val console: Console) {


    companion object{
        val em = Persistence.createEntityManagerFactory("unidadMySQL2").createEntityManager()
    }


    fun addUser(usuario: Usuario){
        try {

            em.transaction.begin()
            em.persist(usuario)
            em.transaction.commit()
            console.mostrarTexto("Usuario: $usuario añadido correctamente")
        }
        catch (e:Exception){
            em.transaction.rollback()
            console.mostrarTexto("Error inesperado; ${e.message}")
        }
    }

    fun getUser(nombreUsuario: String): Usuario {

        try {
            em.transaction.begin()
            val usuario = em.find(Usuario::class.java, nombreUsuario)
            if (usuario != null) return usuario
            else TODO()
        }
        catch (e:Exception){
            em.transaction.rollback()
        }
    }

}