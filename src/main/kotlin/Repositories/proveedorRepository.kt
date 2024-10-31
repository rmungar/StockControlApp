package org.example.Repositories

import jakarta.persistence.Persistence
import org.example.Entitites.Proveedor
import org.example.Utilities.Console

class proveedorRepository() {

    companion object{
        val console = Console
        val em = Persistence.createEntityManagerFactory("unidadMySQL2").createEntityManager()
        fun openTransaction() = em.transaction.begin()
        fun closeTransaction() = em.close()
        fun commitTransaction() = em.transaction.commit()
        fun rollbackTransaction() = em.transaction.rollback()
    }



    fun getProveedor(productoID: String): Proveedor? {

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
            rollbackTransaction()
            return null
        }
        finally {
            closeTransaction()
        }
    }

    fun getTodosProveedores(): List<Proveedor>?{
        try {
            openTransaction()
            val sqlQuery = em.createQuery("FROM Proveedor", Proveedor::class.java)
            val listaProveedores = sqlQuery.resultList
            commitTransaction()
            return listaProveedores
        }
        catch(e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction()
            return null
        }
        finally {
            closeTransaction()
        }
    }

}