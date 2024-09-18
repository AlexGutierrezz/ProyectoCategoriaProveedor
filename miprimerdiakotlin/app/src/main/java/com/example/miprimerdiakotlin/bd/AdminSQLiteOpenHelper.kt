package com.example.miprimerdiakotlin.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
    context: Context,
    name: String = "administracion.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de productos
        db.execSQL(
            """
    CREATE TABLE productos (
        id_producto TEXT PRIMARY KEY,
        id_categoria TEXT,
        id_proveedor TEXT,
        nombre TEXT NOT NULL,
        precio REAL NOT NULL,
        codigo TEXT,
        FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
        FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor)
    )
    """.trimIndent()
        )

        // Crear tabla de categorias
        db.execSQL(
            """
            CREATE TABLE categorias (
                id_categoria TEXT PRIMARY KEY,
                nombre TEXT NOT NULL,
                descripcion TEXT,
                codCate TEXT
            )
            """.trimIndent()
        )

        // Crear tabla de proveedores
        db.execSQL(
            """
            CREATE TABLE proveedores (
                id_proveedor TEXT PRIMARY KEY,
                nombre TEXT NOT NULL,
                direccion TEXT,
                numero_contacto TEXT,
                ci_proveedor TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Manejo de actualizaciones de esquema
        db.execSQL("DROP TABLE IF EXISTS proveedores")
        db.execSQL("DROP TABLE IF EXISTS categorias")
        db.execSQL("DROP TABLE IF EXISTS productos")
        onCreate(db)
    }
}