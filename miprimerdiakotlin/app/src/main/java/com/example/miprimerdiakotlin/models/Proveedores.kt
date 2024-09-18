package com.example.miprimerdiakotlin.models

class Proveedores(nombre: String, direccion: String, numero_contacto: String, id_proveedor: Int) {
    private var nombre: String = nombre
    private var direccion: String = direccion
    private var numero_contacto: String = numero_contacto
    private var id_proveedor: Int = id_proveedor
    // Métodos get y set para nombre
    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    // Métodos get y set para dirección
    fun getDireccion(): String {
        return direccion
    }

    fun setDireccion(direccion: String) {
        this.direccion = direccion
    }

    // Métodos get y set para número de contacto
    fun getNumero_contacto(): String {
        return numero_contacto
    }

    fun setNumero_contacto(numero_contacto: String) {
        this.numero_contacto = numero_contacto
    }

    // Métodos get y set para id_proveedor
    fun getId_proveedor(): Int {
        return id_proveedor
    }

    fun setId_proveedor(id_proveedor: Int) {
        this.id_proveedor = id_proveedor
    }

}