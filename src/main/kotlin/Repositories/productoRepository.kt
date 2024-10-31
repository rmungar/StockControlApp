package org.example.Repositories


import jakarta.persistence.*
import jakarta.transaction.Transactional
import org.example.Entitites.Producto
import org.example.Utilities.Console


class productoRepository() {

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



    fun addProduct(producto: Producto): Boolean {
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            em.persist(producto)
            commitTransaction(em)
            console.mostrarTexto("Usuario: ${producto.nombre} añadido correctamente.")
            return true
        }
        catch (e: Exception) {
            rollbackTransaction(em)
            console.mostrarTexto("Error: ${e.message}")
            return false
        }
        finally {
            closeTransaction(em)
        }
    }


    fun removeProduct(producto: Producto): Boolean {
        val em = emf.createEntityManager()
        try{
            openTransaction(em)
            val productoAEliminar = em.find(producto::class.java, producto.getId())

            if (productoAEliminar != null) {
                em.remove(productoAEliminar)
                commitTransaction(em)
                console.mostrarTexto("Producto ${productoAEliminar.nombre} eliminado correctamente.")
                return true
            }
            else{
                rollbackTransaction(em)
                return false
            }

        }catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction(em)
            return false
        }
        finally {
            closeTransaction(em)
        }
    }


    fun getProduct(productoID: String): Producto? {
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            val productoABuscar = em.find(Producto::class.java, productoID)
            commitTransaction(em)
            return productoABuscar
        }catch (e:Exception){
            rollbackTransaction(em)
            return null
        }finally {
            closeTransaction(em)
        }
    }


    fun getProduct(stock: Boolean): List<Producto>? {
        val em = emf.createEntityManager()
        try {

            openTransaction(em)
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
            rollbackTransaction(em)
            return null
        }finally {
            closeTransaction(em)
        }

    }


    fun modifyProductName(productoID: String, newName: String): Boolean {
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            val productoElegido = getProduct(productoID)
            if (productoElegido != null) {
                productoElegido.nombre = newName
                em.merge(productoElegido)
                commitTransaction(em)
                console.mostrarTexto("Producto ${productoElegido.getId()} modificado correctamente.")
                return true
            }
            else{
                console.mostrarTexto("No se ha podido encontrar el producto con ID: $productoID.")
                rollbackTransaction(em)
                return false
            }
        }
        catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction(em)
            return false
        }
        finally {
            closeTransaction(em)
        }


    }


    fun modifyProductStock(productoID: String, newStock: Int): Boolean {
        val em = emf.createEntityManager()
        try {
            openTransaction(em)
            val productoElegido = getProduct(productoID)
            if (productoElegido != null) {
                productoElegido.stock = newStock
                em.merge(productoElegido)
                commitTransaction(em)
                console.mostrarTexto("Producto ${productoElegido.getId()} modificado correctamente.")
                return true
            }
            else{
                console.mostrarTexto("No se ha podido encontrar el producto con ID: $productoID.")
                rollbackTransaction(em)
                return false
            }
        }
        catch (e:Exception){
            console.mostrarTexto("Error: ${e.message}")
            rollbackTransaction(em)
            return false
        }
        finally {
            closeTransaction(em)
        }

    }
}