package com.example.miprimerdiakotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimerdiakotlin.bd.AdminSQLiteOpenHelper
import com.example.miprimerdiakotlin.models.Productos

class MainActivity : ComponentActivity() {
    private lateinit var btnGuardar: Button
    private lateinit var txtPrecio: EditText
    private lateinit var txtCodigo: EditText
    private lateinit var txtNombre: EditText
    private lateinit var btnBuscarCate: Button
    private lateinit var txtidcate: EditText
    private lateinit var txtNomCate: EditText
    private lateinit var btnBuscarProve: Button
    private lateinit var txtidprove: EditText
    private lateinit var txtNomProveedor: EditText
    lateinit var lispro: ListView
    lateinit var productosList: MutableList<String>
    lateinit var adapterListView: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ajustar los márgenes de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Buscar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        txtPrecio = findViewById(R.id.txtPrecio)
        txtNombre = findViewById(R.id.txtNombreProducto)
        txtCodigo = findViewById(R.id.txtCodigo)
        btnGuardar = findViewById(R.id.btnGuardarProducto)
        btnBuscarCate = findViewById(R.id.btnBuscarcate)
        txtidcate = findViewById(R.id.txtIdCategoria)
        txtNomCate = findViewById(R.id.txtnomcategoria)
        btnBuscarProve = findViewById(R.id.btnBuscarproveedor)
        txtidprove = findViewById(R.id.txtIdProveedor)
        txtNomProveedor = findViewById(R.id.txtnomProveedor)
        lispro = findViewById(R.id.listaProduct)

        productosList = mutableListOf()
        adapterListView = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosList)
        lispro.adapter = adapterListView
    }

    private fun setupListeners() {
        btnGuardar.setOnClickListener { saveProduct() }
        btnBuscarCate.setOnClickListener { searchCategory() }
        btnBuscarProve.setOnClickListener { searchSupplier() }
    }

    private fun saveProduct() {
        val nombre = txtNombre.text.toString().trim()
        val precioStr = txtPrecio.text.toString().trim()
        val codigoStr = txtCodigo.text.toString().trim()
        val idproSrt = txtidcate.text.toString().trim()
        val idcateSrt = txtidprove.text.toString().trim()

        // Validación de campos vacíos
        if (nombre.isEmpty() || precioStr.isEmpty() || codigoStr.isEmpty() || idproSrt.isEmpty() || idcateSrt.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validación del nombre (solo letras)
        if (!nombre.all { it.isLetter() }) {
            Toast.makeText(this, "El nombre del producto solo puede contener letras", Toast.LENGTH_SHORT).show()
            return
        }

        // Validación de tipo
        val precio = precioStr.toDoubleOrNull()
        val codigo = codigoStr.toIntOrNull()
        if (precio == null || codigo == null) {
            Toast.makeText(this, "Código o precio inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db = admin.writableDatabase

        try {
            var codigoValido = false
            var nuevoCodigo: Int? = codigo

            while (!codigoValido) {
                // Comprobar existencia del código
                val cursor = db.rawQuery("SELECT * FROM productos WHERE id_producto = ?", arrayOf(nuevoCodigo.toString()))
                if (cursor.moveToFirst()) {
                    // Si el código ya existe, pide un nuevo código
                    Toast.makeText(this, "El código de producto ya existe, ingrese otro código", Toast.LENGTH_SHORT).show()
                    // Aquí deberías pedir al usuario que ingrese un nuevo código
                    // Este código se puede obtener, por ejemplo, a través de un Dialog o Input.
                    // Para simplificar, puedes usar el siguiente método (sólo un ejemplo):

                } else {
                    codigoValido = true // El código es válido y no existe
                }
                cursor.close()
            }

            // Insertar nuevo producto
            val producto = Productos(
                nuevoCodigo ?: 0,
                txtidcate.text.toString().toIntOrNull() ?: 0,
                txtidprove.text.toString().toIntOrNull() ?: 0,
                nombre,
                precio
            )

            val registro = ContentValues().apply {
                put("id_producto", producto.getIdProducto())
                put("id_categoria", producto.getIdCategoria())
                put("id_proveedor", producto.getIdProveedor())
                put("nombre", producto.getNombre())
                put("precio", producto.getPrecio())
            }
            db.insert("productos", null, registro)

            Log.d("PRODUCTO", "Producto insertado: ${producto.getIdProducto()} ${producto.getNombre()}")

            // Actualizar UI
            productosList.add("${producto.getIdProducto()}  ${producto.getNombre()}  ${producto.getPrecio()}  ${producto.getIdProveedor()}  ${producto.getIdCategoria()}")
            adapterListView.notifyDataSetChanged() // Asegúrate de actualizar el adapter

            Toast.makeText(this, "Se cargaron los datos del producto", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("Base de datos", "Error al insertar producto: ${e.message}")
            Toast.makeText(this, "Error al guardar el producto", Toast.LENGTH_SHORT).show()
        } finally {
            db.close()
        }

        // Limpiar campos
        clearFields()
    }

    // Método para pedir un nuevo código (deberás implementarlo)

    private fun searchCategory() {
        val categoriaId = txtidcate.text.toString().trim()
        if (categoriaId.isNotEmpty()) {
            val admin = AdminSQLiteOpenHelper(this)
            val db = admin.readableDatabase
            val cursor = db.rawQuery("SELECT nombre, descripcion, id_categoria FROM categorias WHERE id_categoria = ?", arrayOf(categoriaId))
            if (cursor.moveToFirst()) {
                txtidcate.setText(cursor.getString(2))
                txtNomCate.setText(cursor.getString(0))
            } else {
                Toast.makeText(this, "No existe una categoría con dicho código", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            db.close()
        } else {
            Toast.makeText(this, "Por favor ingrese un código de categoría", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchSupplier() {
        val proveedorId = txtidprove.text.toString().trim()
        if (proveedorId.isNotEmpty()) {
            val admin = AdminSQLiteOpenHelper(this)
            val db = admin.readableDatabase
            val cursor = db.rawQuery("SELECT nombre, direccion, numero_contacto, id_proveedor FROM proveedores WHERE id_proveedor = ?", arrayOf(proveedorId))
            if (cursor.moveToFirst()) {
                txtidprove.setText(cursor.getString(3))
                txtNomProveedor.setText(cursor.getString(0))
            } else {
                Toast.makeText(this, "No existe un proveedor con dicho código", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            db.close()
        } else {
            Toast.makeText(this, "Por favor ingrese un código de proveedor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        txtCodigo.setText("")
        txtNombre.setText("")
        txtPrecio.setText("")
        txtNomCate.setText("")
        txtNomProveedor.setText("")
        txtidcate.setText("")
        txtidprove.setText("")
    }
}