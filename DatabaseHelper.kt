package com.example.seguridadivanti

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla Activos
        val createTableActivos = """
            CREATE TABLE IF NOT EXISTS Activos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo_barras TEXT UNIQUE,
                codigo_patrimonial TEXT,
                usuario_asignado TEXT,
                marca TEXT,
                modelo TEXT
            )
        """.trimIndent()
        db.execSQL(createTableActivos)

        // Crear tabla Registros
        val createTableRegistros = """
            CREATE TABLE IF NOT EXISTS Registros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo_barras TEXT,
                tipo TEXT,
                fecha_hora TEXT,
                observaciones TEXT
            )
        """.trimIndent()
        db.execSQL(createTableRegistros)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Activos")
        db.execSQL("DROP TABLE IF EXISTS Registros")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SeguridadIvanti.db"
    }
}