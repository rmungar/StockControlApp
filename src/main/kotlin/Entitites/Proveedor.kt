package org.example.Entitites

import jakarta.persistence.*


@Entity
@Table(name = "Proveedores")
data class Proveedor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idProveedor: Long? = null,

    @Column(name="nombre proveedor",nullable = false)
    val nombreProveedor: String,

    @Column(name="direccion proveedor", nullable = false)
    val direccionProveedor: String,

    @OneToMany(mappedBy = "proveedor", cascade = [CascadeType.ALL])
    @Column(name="lista productos", nullable = false)
    val productos: List<Producto>?

) {

    override fun toString(): String {
        var retorno = "Proveedor: $nombreProveedor con id $idProveedor y sede en $direccionProveedor provee:"
        for (producto in productos!!) {
            retorno += "${producto}\n "
        }
        return retorno
    }

}