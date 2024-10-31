package org.example

import org.example.Entitites.Usuario
import org.example.Services.productoService
import org.example.Services.proveedorService
import org.example.Services.usuarioService
import org.example.Utilities.Console
import org.example.Utilities.Program

fun main() {

    val usuarioService = usuarioService()
    val productoService = productoService()
    val proveedorService = proveedorService()
    val console = Console



    //usuarioService.addUsuario(Usuario("jp3", "pythonkotlin"))

    val programa = Program(productoService, proveedorService, usuarioService, console)

    programa.funcionar()

}