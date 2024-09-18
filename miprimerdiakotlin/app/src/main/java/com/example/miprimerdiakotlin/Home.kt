package com.example.miprimerdiakotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimerdiakotlin.models.Productos

class Home : AppCompatActivity() {
    lateinit var btnCategorias: CardView
    lateinit var btnProductos: CardView
    lateinit var btnProveedores: CardView
    lateinit var btnBuscarr: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Ajustar el padding para las ventanas de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Buscar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar las vistas
        cargarR()

        // Configurar los eventos de los botones
        estadoBoton()
    }

    // Inicializa las vistas con los IDs correspondientes
    fun cargarR() {
        btnCategorias = findViewById(R.id.btn_categorias)
        btnProductos = findViewById(R.id.btnProductos)
        btnProveedores = findViewById(R.id.btnProveedores)
        btnBuscarr = findViewById(R.id.btnBuscar)
    }

    // Configura los eventos de los botones
    fun estadoBoton() {
        btnCategorias.setOnClickListener {
            val intent = Intent(this, registro_categoria::class.java)
            startActivity(intent)
        }

        btnProductos.setOnClickListener {
           finish()
        }

        btnProveedores.setOnClickListener {
            val intent = Intent(this, registro_proveedor::class.java)
            startActivity(intent)
        }

        btnBuscarr.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}