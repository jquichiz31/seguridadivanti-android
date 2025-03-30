package com.example.seguridadivanti

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)
        cargarCSVEnSQLite(this, dbHelper)  // Cargar el archivo CSV en la base de datos

        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        btnEntrar.setOnClickListener {
            val intent = Intent(this, RegistroActivosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarCSVEnSQLite(context: Context, dbHelper: DatabaseHelper) {
        val db = dbHelper.writableDatabase
        try {
            val inputStream = context.assets.open("activos.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLine() // Ignorar la primera línea (encabezados)

            reader.forEachLine { line ->
                val tokens = line.split(",")
                if (tokens.size == 5) {
                    val values = ContentValues().apply {
                        put("codigo_barras", tokens[0])
                        put("codigo_patrimonial", tokens[1])
                        put("usuario_asignado", tokens[2])
                        put("marca", tokens[3])
                        put("modelo", tokens[4])
                    }
                    db.insert("Activos", null, values)
                }
            }
            reader.close()
            Toast.makeText(context, "Datos cargados correctamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al cargar CSV: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}