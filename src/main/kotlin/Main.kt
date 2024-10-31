package org.example

import org.example.Entitites.Usuario
import org.example.Services.productoService
import org.example.Services.proveedorService
import org.example.Services.usuarioService
import org.example.Utilities.Console
import org.example.Utilities.Program

fun main() {

    // Existe un usuario en la base de datos, aunque se pueden crear más.
    // El programa pide un usuario y contraseña cada vez que se lanza el programa. Si se introduce uno que no existe, el programa
    // preguntará si quieres añadir dicho usuario.



    val usuarioService = usuarioService()
    val productoService = productoService()
    val proveedorService = proveedorService()
    val console = Console



    val programa = Program(productoService, proveedorService, usuarioService, console)

    programa.funcionar()

}