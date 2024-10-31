package org.example.Services

import org.example.Entitites.Producto
import org.example.Repositories.productoRepository
import org.example.Repositories.proveedorRepository

class productoService {

    companion object{

        private val productoRepository = productoRepository()

    }




    fun addProducto(producto: Producto): Boolean {
        return productoRepository.addProduct(producto)
    }


    fun removeProducto(producto: Producto): Boolean {
        return productoRepository.removeProduct(producto)
    }

    fun getProducto(productoID: String): Producto? {
        return productoRepository.getProduct(productoID)
    }

    fun getProducto(stock: Boolean): List<Producto>? {
        return productoRepository.getProduct(stock)
    }

    fun modificarNombreProducto(productoID: String, newName: String): Boolean{
        return productoRepository.modifyProductName(productoID, newName)
    }

    fun modificarStockProducto(productoID: String, stock: Int): Boolean {
        return productoRepository.modifyProductStock(productoID, stock)
    }
}