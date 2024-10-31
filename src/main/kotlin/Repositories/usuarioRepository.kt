package org.example.Repositories

import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional
import org.example.Entitites.Usuario
import org.example.Utilities.Console


class usuarioRepository() {


    companion object{
        val console = Console
        val emf= Persistence.createEntityManagerFactory("unidadMySQL2")
        fun openTransaction(em: EntityManager){
            em.transaction.begin()
        }
        fun closeTransaction(em: EntityManager) {
            em.close()
        }
        fun commitTransaction(em: EntityManager) {
            em.transaction.commit()
        }
        fun rollbackTransaction(em: EntityManager) {
            em.transaction.rollback()
        }
    }


    fun addUser(usuario: Usuario){
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            em.persist(usuario)
            commitTransaction(em)
            console.mostrarTexto("Usuario: ${usuario.nombre} añadido correctamente.")

        }
        catch (e:Exception){
            rollbackTransaction(em)
            console.mostrarTexto("Error: ${e.message}.")

        }
        finally {
            closeTransaction(em)
        }
    }


    fun getUser(nombreUsuario: String): Usuario? {
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            val usuario = em.find(Usuario::class.java, nombreUsuario)
            commitTransaction(em)
            if (usuario != null) return usuario
            else {
                console.mostrarTexto("El usuario '${nombreUsuario}' no encontrado.")
                return null
            }
        }
        catch (e:Exception){
            rollbackTransaction(em)
            console.mostrarTexto("Error inesperado; ${e.message}.")
            return null
        }
        finally {
            closeTransaction(em)
        }
    }




}