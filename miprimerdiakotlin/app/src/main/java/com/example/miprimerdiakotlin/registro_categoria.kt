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
import com.example.miprimerdiakotlin.models.Categorias

class registro_categoria : AppCompatActivity() {
    private lateinit var btnGuardar: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtCodCate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_categoria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categoria)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoBoton()
    }
    private fun cargarR() {
        txtNombre = findViewById(R.id.txtNombreCategoria)
        txtDescripcion = findViewById(R.id.txtDescripcionCategoria)
        txtCodCate = findViewById(R.id.txtCodigoCategoria)
        btnGuardar = findViewById(R.id.btnGuardarCategoria)
    }
    private fun estadoBoton() {
        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val codCate = txtCodCate.text.toString().toInt()


          
                val adminsql = AdminSQLiteOpenHelper(this)
                val db = adminsql.writableDatabase
                val registro = ContentValues()

                // Realizando la instancia del objeto
                val objeto = Categorias(nombre, descripcion, codCate)

                registro.put("id_categoria", objeto.getIdCategoriasC())
                registro.put("nombre", objeto.getNombreC())
                registro.put("descripcion", objeto.getDescripcionC())

                db.insert("categorias", null, registro)
                db.close()

                txtCodCate.setText("")
                txtNombre.setText("")
                txtDescripcion.setText("")
                Toast.makeText(this, "Datos de categoría cargados", Toast.LENGTH_SHORT).show()

        }
    }
}