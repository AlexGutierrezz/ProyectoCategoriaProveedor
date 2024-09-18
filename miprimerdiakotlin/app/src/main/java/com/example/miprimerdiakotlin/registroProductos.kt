package com.example.miprimerdiakotlin

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimerdiakotlin.bd.AdminSQLiteOpenHelper
import com.example.miprimerdiakotlin.models.Productos

class registroProductos : AppCompatActivity() {
    private lateinit var btnGuardar: Button
    private lateinit var txtPrecio: EditText
    private lateinit var txtCodigo: EditText
    private lateinit var txtNombre: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Buscar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoBoton()
    }
    private fun cargarR() {
        txtPrecio = findViewById(R.id.txtPrecio)
        txtNombre = findViewById(R.id.txtNombreProducto)
        txtCodigo = findViewById(R.id.txtCodigo)
        btnGuardar = findViewById(R.id.btnGuardarProducto)
    }
    fun estadoBoton(){
        btnGuardar.setOnClickListener{

            val adminsql = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = adminsql.writableDatabase
            val registro = ContentValues()

            registro.put("id_productos", txtCodigo.text.toString())
            registro.put("nombre", txtNombre.text.toString())
            registro.put("precio", txtPrecio.text.toString())
            db.insert("productos", null, registro)
            db.close()
            txtCodigo.setText("")
            txtNombre.setText("")
            txtPrecio.setText("")
            Toast.makeText(this, "Se cargaron los datos del producto", Toast.LENGTH_SHORT).show()
// productosList.add(producto.getIdProducto().toString()+" "+ producto.getNombre() + "," +producto.getPrecio()+" "+producto.getIdCategoria().toString()+" "+producto.getIdProveedor().toString())
            //lispro.adapter=adapterListView
        }
        /*private fun cargarListaProductos(){
        // val productos= arrayOf("laptop", "mouse")
        adapterListView =ArrayAdapter(this,android.R.layout.simple_list_item_1,productosList)
        lispro.adapter=adapterListView
    }*/
    }
}