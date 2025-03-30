package com.example.seguridadivanti

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_activos)

        dbHelper = DatabaseHelper(this)
        db = dbHelper.writableDatabase

        val inputCodigoBarras = findViewById<EditText>(R.id.inputCodigoBarras)
        val btnEntrada = findViewById<Button>(R.id.btnEntrada)
        val btnSalida = findViewById<Button>(R.id.btnSalida)
        val btnVerHistorial = findViewById<Button>(R.id.btnVerHistorial)
        val textResultado = findViewById<TextView>(R.id.textResultado)

        btnEntrada.setOnClickListener {
            val codigo = inputCodigoBarras.text.toString().trim()
            if (codigo.isNotEmpty()) {
                registrarMovimiento(codigo, "ENTRADA")
            }
        }

        btnSalida.setOnClickListener {
            val codigo = inputCodigoBarras.text.toString().trim()
            if (codigo.isNotEmpty()) {
                registrarMovimiento(codigo, "SALIDA")
            }
        }

        btnVerHistorial.setOnClickListener {
            val codigo = inputCodigoBarras.text.toString().trim()
            if (codigo.isNotEmpty()) {
                val intent = Intent(this, HistorialRegistrosActivity::class.java)
                intent.putExtra("codigo_barras", codigo)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingrese un código válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registrarMovimiento(codigo: String, tipo: String) {
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val values = ContentValues().apply {
            put("codigo_barras", codigo)
            put("tipo", tipo)
            put("fecha_hora", currentDateTime)
            put("observaciones", "")
        }
        db.insert("Registros", null, values)
    }
}