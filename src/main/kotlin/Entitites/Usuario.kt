package org.example.Entitites

import jakarta.persistence.*

@Entity
@Table(name = "Usuarios")

data class Usuario(

    @Id
    @Column(name = "nombre usuario",nullable = false)
    private val _nombre: String,

    @Column(name = "password usuario", nullable = false, length = 20)
    private val _psswd: String
)
{

    override fun toString(): String {
        return "Usuario $_nombre con contraseña $_psswd"
    }

}