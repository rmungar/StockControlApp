package org.example.Utilities

object Console {


    fun mostrarTexto(texto: String = "", salto: Boolean = true) {

        if (salto) println(texto)
        else print(texto)

    }

    fun getInput(): String {
        return readln()
    }

}