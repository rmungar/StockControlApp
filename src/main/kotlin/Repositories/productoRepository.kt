package org.example.Repositories


import jakarta.persistence.*
import org.example.Entitites.Producto
import org.example.Utilities.Console


class productoRepository() {

    companion object{
        val console = Console
        val em = Persistence.createEntityManagerFactory("unidadMySQL2").createEntityManager()
        fun openTransaction() = em.transaction.begin()
        fun closeTransaction() = em.close()
        fun commitTransaction() = em.transaction.commit()
        fun rollbackTransaction() = em.transaction.rollback()
    }

    fun addProduct(producto: Producto): Boolean {

        try {
            openTransaction()
            em.persist(producto)
            commitTransaction()
            console.mostrarTexto("Usuario: ${producto.nombre} añadido correctamente.")
            return true
        }
        catch (e: Exception) {
            rollbackTransaction()
            console.mostrarTexto("Error: ${e.message}")
            return false
        }
        finally {
            closeTransaction()
        }
    }

    fun removeProduct(producto: Producto): Boolean {
        try{
            openTransaction()
            val productoAEliminar = em.find(producto::class.java, producto.getId())

            if (productoAEliminar != null) {
                em.remove(productoAEliminar)
                commitTransaction()
                console.mostrarTexto("Producto ${productoAEliminar.nombre} eliminado correctamente.")
                return true
            }
            else{
                rollbackTransaction()
                return false
            }

        }catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction()
            return false
        }
        finally {
            closeTransaction()
        }
    }

    fun getProduct(productoID: String): Producto? {

        try {
            openTransaction()
            val productoABuscar = em.find(Producto::class.java, productoID)
            commitTransaction()
            return productoABuscar
        }catch (e:Exception){
            rollbackTransaction()
            return null
        }finally {
            closeTransaction()
        }
    }

    fun getProduct(stock: Boolean): List<Producto>? {

        try {
            openTransaction()
            if (stock){
                val sqlQuery: Query = em.createQuery("FROM Producto WHERE stock > 0", Producto::class.java)
                val listaProductos = sqlQuery.resultList as List<Producto>?
                return listaProductos
            }
            else{
                val sqlQuery: Query = em.createQuery("FROM Producto WHERE stock = 0", Producto::class.java)
                val listaProductos = sqlQuery.resultList as List<Producto>?
                return listaProductos
            }
        }catch (e:Exception){
            rollbackTransaction()
            return null
        }finally {
            closeTransaction()
        }

    }

    fun modifyProductName(productoID: String, newName: String): Boolean {

        try {
            openTransaction()
            val productoElegido = getProduct(productoID)
            if (productoElegido != null) {
                productoElegido.nombre = newName
                em.merge(productoElegido)
                commitTransaction()
                console.mostrarTexto("Producto ${productoElegido.getId()} modificado correctamente.")
                return true
            }
            else{
                console.mostrarTexto("No se ha podido encontrar el producto con ID: $productoID.")
                rollbackTransaction()
                return false
            }
        }
        catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction()
            return false
        }
        finally {
            closeTransaction()
        }


    }

    fun modifyProductStock(productoID: String, newStock: Int): Boolean {

        try {
            openTransaction()
            val productoElegido = getProduct(productoID)
            if (productoElegido != null) {
                productoElegido.stock = newStock
                em.merge(productoElegido)
                commitTransaction()
                console.mostrarTexto("Producto ${productoElegido.getId()} modificado correctamente.")
                return true
            }
            else{
                console.mostrarTexto("No se ha podido encontrar el producto con ID: $productoID.")
                rollbackTransaction()
                return false
            }
        }
        catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            return false
        }
        finally {
            closeTransaction()
        }

    }
}