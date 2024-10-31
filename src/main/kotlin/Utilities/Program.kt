package org.example.Utilities

import org.example.Entitites.Producto
import org.example.Entitites.Proveedor
import org.example.Entitites.Usuario
import org.example.Services.productoService
import org.example.Services.proveedorService
import org.example.Services.usuarioService


class Program(
    val productoService: productoService,
    val proveedorService: proveedorService,
    val usuarioService: usuarioService,
    val console: Console
) {


    private fun login(): Boolean{

        console.mostrarTexto("Ingrese el nombre de usuario: ", false)
        val nombreUsuario = console.getInput()
        console.mostrarTexto()
        console.mostrarTexto("Ingrese su contraseña: ", false)
        val psswd = console.getInput()

        val respuesta = usuarioService.getUsuario(nombreUsuario)
        if (respuesta != null) {
            if (respuesta.getPassword() == psswd){

                console.mostrarTexto("Bienvenido, $nombreUsuario!")
                return true
            }
        }
        else{
            while (true){

                console.mostrarTexto("Ese usuario no existe en la base de datos, ¿quiere añadirlo? (y / n) ", false)

                val entrada = console.getInput()

                console.mostrarTexto()

                if (entrada == "y" || entrada == "Y"){

                    val usuario = Usuario(nombreUsuario, psswd)

                    usuarioService.addUsuario(usuario)

                    console.mostrarTexto("Has sido añadido, bienvenido $nombreUsuario!")

                    return true
                }
                else if (entrada == "n" || entrada == "N"){

                    console.mostrarTexto("Hasta la próxima entonces")
                    return false

                }
                else{

                    console.mostrarTexto("El valor ingresado no era una opción.")

                }
            }
        }

        return false
    }


    private fun menu(): Int{

        val validOptions = listOf("0","1","2","3","4","5","6","7","8","9","10")

        while (true){

            console.mostrarTexto("¿Qué desea hacer?")
            console.mostrarTexto("1 - Añadir un usuario")
            console.mostrarTexto("2 - Dar de alta un producto")
            console.mostrarTexto("3 - Dar de baja un producto")
            console.mostrarTexto("4 - Modificar el nombre de un producto")
            console.mostrarTexto("5 - Modificar el stock de un producto")
            console.mostrarTexto("6 - Obtener 1 producto")
            console.mostrarTexto("7 - Obtener todos los productos con stock")
            console.mostrarTexto("8 - Obtener todos los productos sin stock")
            console.mostrarTexto("9 - Obtener 1 proveedor")
            console.mostrarTexto("10 - Obtener todos los proveedores")
            console.mostrarTexto("0 - Salir")
            console.mostrarTexto("> ", false)
            val opcion = console.getInput()

            console.mostrarTexto()
            if (validOptions.contains(opcion)){
                return opcion.toInt()
            }
            else{
                console.mostrarTexto("El valor ingresado no era válido.")
                console.mostrarTexto()
            }


        }


    }


    fun funcionar(){

        val loginValido = login()


        if (loginValido){

            while (true){
                val opcion = menu()

                when(opcion){

                    1 -> {

                        val usuario = addUsuario()
                        usuarioService.addUsuario(usuario)
                    }
                    2 -> {

                        val producto = addProducto()
                        if (producto != null){
                            productoService.addProducto(producto)
                        }


                    }
                    3 -> {

                        removeProducto()

                    }
                    4 -> {

                        cambiarNombre()

                    }
                    5 -> {

                        cambiarStock()

                    }
                    6 -> {

                        val producto = getProducto()

                        if (producto != null){
                            console.mostrarTexto(producto.toString())
                        }
                        else{
                            console.mostrarTexto("No existe ese producto en la base de datos")
                        }

                    }
                    7 -> {

                        val productosConStock = getProductosConStock()
                        if (productosConStock != null){
                            if (productosConStock.isNotEmpty()){
                                console.mostrarTexto()
                                console.mostrarTexto("Lista de productos")
                                productosConStock.forEach {
                                    console.mostrarTexto(it.toString())
                                }
                            }
                            else{
                                console.mostrarTexto("No existen productos con stock en la base de datos")
                            }

                        }
                        else{
                            console.mostrarTexto("No existen productos con stock en la base de datos")
                        }

                    }
                    8 -> {

                        val productosSinStock = getProductosSinStock()
                        if (productosSinStock != null){
                            if (productosSinStock.isNotEmpty()){
                                console.mostrarTexto()
                                console.mostrarTexto("Lista de productos")
                                productosSinStock.forEach {
                                    console.mostrarTexto(it.toString())
                                }
                            }
                            else{
                                console.mostrarTexto("No existen productos sin stock en la base de datos")
                            }
                        }
                        else{
                            console.mostrarTexto("No existen productos sin stock en la base de datos")
                        }
                    }
                    9 -> {

                        val proveedor = getProveedor()
                        if (proveedor != null){
                            console.mostrarTexto(proveedor.toString())
                        }
                        else{
                            console.mostrarTexto("No existe ningún proveedor para ese producto.")
                        }

                    }
                    10 -> {

                        val proveedores = getAllProveedores()

                        if (proveedores != null){
                            if (proveedores.isNotEmpty()){
                                console.mostrarTexto()
                                console.mostrarTexto("Lista de proveedores")
                                proveedores.forEach {
                                    console.mostrarTexto(it.toString())
                                }
                            }
                            else{
                                console.mostrarTexto("No existen proveedores en la base de datos")
                            }

                        }
                        else{
                            console.mostrarTexto("No existen proveedores en la base de datos")
                        }

                    }
                    0 -> {
                        break
                    }
                    else -> {
                        console.mostrarTexto("Eso no es una opción válida")
                        console.mostrarTexto()
                    }
                }

            }

        }


    }


    private fun addUsuario(): Usuario {

        var nombre = ""
        var psswd = ""
        while (nombre == ""){
            console.mostrarTexto("Ingrese el nombre:", false)
            nombre = console.getInput()
            console.mostrarTexto()
        }
        while (psswd == ""){
            console.mostrarTexto("Ingrese la contraseña:", false)
            psswd = console.getInput()
            console.mostrarTexto()
        }

        return Usuario(nombre, psswd)


    }

    private fun addProducto(): Producto? {
        var nombre = ""
        var categoria = ""
        var descripcion = ""
        var precioSinIva = 0F
        val stock : Int

        try {
            while (nombre == ""){
                console.mostrarTexto("Ingrese el nombre del producto: ", false)
                nombre = console.getInput()
            }
            while (categoria == ""){
                console.mostrarTexto("Ingrese la categoría del producto: ", false)
                categoria = console.getInput()
            }
            while (descripcion == ""){
                console.mostrarTexto("Ingrese la descripción del producto: ", false)
                descripcion = console.getInput()
            }
            while (precioSinIva == 0F){
                console.mostrarTexto("Ingrese el precio SIN IVA del producto: ", false)
                precioSinIva = console.getInput().toFloat()
            }
            console.mostrarTexto("Ingrese el stock del prodcuto :", false)
            stock = console.getInput().toInt()

            val proveedor = proveedorService.getProveedor(pedirIDProveedor())

            val producto = Producto(nombre, categoria, descripcion, precioSinIva, stock, proveedor)

            return producto

        }

        catch (e: Exception){
            console.mostrarTexto("Error: ${e.message}")
            return null
        }
    }

    private fun removeProducto(){

        val producto = getProducto()

        if (producto != null){
            productoService.removeProducto(producto)
        }

    }

    private fun cambiarNombre() {

        val producto = getProducto()

        if (producto != null){
            console.mostrarTexto("Ingrese el nuevo nombre del producto: ", false)
            val nombre = console.getInput()
            console.mostrarTexto()
            productoService.modificarNombreProducto(producto.getId(), nombre)
        }


    }

    private fun cambiarStock() {

        try {
            val producto = getProducto()

            if (producto != null){
                console.mostrarTexto("Ingrese el nuevo nombre del producto: ", false)
                val stock = console.getInput().toInt()
                console.mostrarTexto()
                productoService.modificarStockProducto(producto.getId(), stock)
            }
        }
        catch(e:NumberFormatException){
            console.mostrarTexto("Error: ${e.message}")
        }
    }

    private fun getProducto(): Producto? {

        console.mostrarTexto("Ingrese el ID del producto: ", false)
        val id = console.getInput()
        console.mostrarTexto()
        val producto = productoService.getProducto(id)

        return producto
    }

    private fun getProductosConStock(): List<Producto>? {
        return productoService.getProducto(true)
    }

    private fun getProductosSinStock(): List<Producto>? {
        return productoService.getProducto(false)
    }

    private fun getProveedor(): Proveedor? {

        var productoID = ""
        while (productoID == ""){
            console.mostrarTexto("Ingrese el ID del producto: ", false)
            productoID = console.getInput()
        }

        val proveedor = proveedorService.getProveedor(productoID)

        return proveedor
    }

    private fun getAllProveedores(): List<Proveedor>? {
        return proveedorService.getTodosProveedores()
    }

    private fun pedirIDProveedor(): Long {

        while (true){
            console.mostrarTexto("Ingrese el id del proveedor: ", false)
            val id = console.getInput().toLongOrNull()
            console.mostrarTexto()
            if (id != null){
                return id
            }
        }

    }
}