package org.example.Services

import org.example.Entitites.Proveedor
import org.example.Repositories.proveedorRepository

class proveedorService {

    companion object{
        private val proveedorRepository = proveedorRepository()
    }


    fun getProveedor(productoID: String) : Proveedor?{
        return proveedorRepository.getProveedor(productoID)
    }

    fun getTodosProveedores(): List<Proveedor>? {
        return proveedorRepository.getTodosProveedores()
    }


}