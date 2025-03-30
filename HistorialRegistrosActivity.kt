package com.example.seguridadivanti

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistorialRegistrosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var txtHistorial: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_registros)

        dbHelper = DatabaseHelper(this)
        db = dbHelper.readableDatabase

        txtHistorial = findViewById(R.id.txtHistorial)

        val codigoBarras = intent.getStringExtra("codigo_barras") ?: ""

        mostrarHistorial(codigoBarras)
    }

    private fun mostrarHistorial(codigo: String) {
        val cursor = db.rawQuery(
            "SELECT tipo, fecha_hora FROM Registros WHERE codigo_barras = ? ORDER BY fecha_hora DESC",
            arrayOf(codigo)
        )

        val historial = StringBuilder()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val tipo = cursor.getString(0)
                val fechaHora = cursor.getString(1)
                historial.append("[$fechaHora] - $tipo\n")
            }
            txtHistorial.text = historial.toString()
        } else {
            txtHistorial.text = "No se encontraron registros para este equipo."
        }
        cursor.close()
    }
}