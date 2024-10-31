package org.example.Repositories

import jakarta.persistence.Persistence
import org.example.Entitites.Usuario
import org.example.Utilities.Console


class usuarioRepository() {


    companion object{
        val console = Console
        val em = Persistence.createEntityManagerFactory("unidadMySQL2").createEntityManager()
        fun openTransaction() = em.transaction.begin()
        fun closeTransaction() = em.close()
        fun commitTransaction() = em.transaction.commit()
        fun rollbackTransaction() = em.transaction.rollback()
    }


    fun addUser(usuario: Usuario){
        try {

            em.transaction.begin()
            em.persist(usuario)
            em.transaction.commit()
            console.mostrarTexto("Usuario: ${usuario.nombre} añadido correctamente.")

        }
        catch (e:Exception){
            em.transaction.rollback()
            console.mostrarTexto("Error: ${e.message}.")

        }
        finally {
            em.close()
        }
    }

    fun getUser(nombreUsuario: String): Usuario? {

        try {
            em.transaction.begin()
            val usuario = em.find(Usuario::class.java, nombreUsuario)
            if (usuario != null) return usuario
            else {
                console.mostrarTexto("El usuario '${nombreUsuario}' no encontrado.")
                return null
            }
        }
        catch (e:Exception){
            em.transaction.rollback()
            console.mostrarTexto("Error inesperado; ${e.message}.")
            return null
        }
        finally {
            em.close()
        }
    }




}