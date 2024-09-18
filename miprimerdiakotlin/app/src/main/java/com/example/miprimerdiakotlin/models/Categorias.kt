package com.example.miprimerdiakotlin.models

class Categorias(
    var nombre: String,
    var descripcion: String,
    var idCategorias: Int
) {
    // Métodos get y set para nombre
    fun getNombreC(): String = nombre

    fun setNombreC(nombre: String) {
        this.nombre = nombre
    }

    // Métodos get y set para descripción
    fun getDescripcionC(): String = descripcion

    fun setDescripcionC(descripcion: String) {
        this.descripcion = descripcion
    }

    // Métodos get y set para id_categoria
    fun getIdCategoriasC(): Int = idCategorias

    fun setIdCategoriasC(idCategorias: Int) {
        this.idCategorias = idCategorias
    }
}