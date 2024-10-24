package org.example.Entitites

import jakarta.persistence.*
import java.time.Instant
import java.util.Date



@Entity
@Table(name = "Productos")
data class Producto(

    @Column(nullable = false)
    val nombre: String,

    @Column(nullable = false)
    val categoria: String,

    @Column(nullable = false)
    val descripcion: String,

    @Column(name= "precio sin iva",nullable = false)
    val precioSinIva: Float,

    @Column(nullable = false)
    var stock: Int,

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    val proveedor: Proveedor? = null

    ) {

    @Id
    private var id: String = ""

    @Column(name = "precio con iva", nullable = false)
    private var precioConIva: Float = 0.0F

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private val fechaAlta: Date = Date.from(Instant.now())

    init {

        id = nombre.split("")[0] + nombre.split("")[1] + categoria.split("")[0] + categoria.split("")[1] + proveedor!!.nombreProveedor.split("")[0] + proveedor!!.nombreProveedor.split("")[1]

        precioConIva = (precioSinIva + (precioSinIva * 0.21)).toFloat()

    }


    override fun toString(): String {
        return "El producto: $nombre fue añadido el $fechaAlta por ${proveedor!!.nombreProveedor} con un precio de $precioConIva."
    }


}