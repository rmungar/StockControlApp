package org.example.Repositories

import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional
import org.example.Entitites.Proveedor
import org.example.Utilities.Console

class proveedorRepository() {

    companion object{
        val console = Console
        val emf = Persistence.createEntityManagerFactory("unidadMySQL2")
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



    fun getProveedor(productoID: String): Proveedor? {
        val em = emf.createEntityManager()
        try {
            val listaProveedores = getTodosProveedores()
            if (listaProveedores != null) {
                for (proveedor in listaProveedores) {
                    if (proveedor.productos != null){
                        for (producto in proveedor.productos) {
                            if(producto.getId() == productoID) return proveedor
                        }
                    }
                }
            }
            return null
        }

        catch(e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction(em)
            return null
        }
        finally {
            closeTransaction(em)
        }
    }


    fun getTodosProveedores(): List<Proveedor>?{
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            val sqlQuery = em.createQuery("FROM Proveedor", Proveedor::class.java)
            val listaProveedores = sqlQuery.resultList
            commitTransaction(em)
            return listaProveedores
        }
        catch(e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction(em)
            return null
        }
        finally {
            closeTransaction(em)
        }
    }

}