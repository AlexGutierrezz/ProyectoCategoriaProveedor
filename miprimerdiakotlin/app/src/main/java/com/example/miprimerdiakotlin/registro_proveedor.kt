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
import com.example.miprimerdiakotlin.models.Proveedores

class registro_proveedor : AppCompatActivity() {
    private lateinit var btnGuardar: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtNumeroContacto: EditText
    private lateinit var txtci: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_proveedor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.proveedor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarComponentes()
        estadoBoton()


    }
    private fun cargarComponentes() {
        txtNombre = findViewById(R.id.txtNombreProveedor)
        txtDireccion = findViewById(R.id.txtDireccionProveedor)
        txtNumeroContacto = findViewById(R.id.txtNumeroContacto)
        txtci = findViewById(R.id.txtCiProveedor)
        btnGuardar = findViewById(R.id.btnGuardarProveedor)
    }
    private fun estadoBoton() {
        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val direccion = txtDireccion.text.toString()
            val numeroContacto = txtNumeroContacto.text.toString()
            val ciProveedor = txtci.text.toString().toInt()

                val adminsql = AdminSQLiteOpenHelper(this, )
                val db = adminsql.writableDatabase
                val registro = ContentValues()

                // Realizando la instancia del objeto
                val proveedor = Proveedores(nombre, direccion, numeroContacto, ciProveedor)

                registro.put("id_proveedor", proveedor.getId_proveedor())
                registro.put("nombre", proveedor.getNombre())
                registro.put("direccion", proveedor.getDireccion())
                registro.put("numero_contacto", proveedor.getNumero_contacto())

                db.insert("proveedores", null, registro)
                db.close()

                txtci.setText("")
                txtNombre.setText("")
                txtDireccion.setText("")
                txtNumeroContacto.setText("")

                Toast.makeText(this, "Datos del proveedor cargados", Toast.LENGTH_SHORT).show()

        }
    }
}