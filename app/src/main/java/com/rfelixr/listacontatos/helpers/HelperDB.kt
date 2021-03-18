package com.rfelixr.listacontatos.helpers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rfelixr.listacontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, NAME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NAME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val COLUMNS_ID = "id"
    val COLUMNS_NOME = "nome"
    val COLUMNS_TELEFONE = "telefone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NOME TEXT NOT NULL," +
            "$COLUMNS_TELEFONE TEXT NOT NULL," +
            "" +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT))"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (newVersion != oldVersion)
            db?.execSQL(DROP_TABLE)

        onCreate(db)
    }

    fun buscarContato(busca: String) : List<ContatosVO>{

        val db :SQLiteDatabase = readableDatabase ?: return mutableListOf()
        val lista :MutableList<ContatosVO> = mutableListOf<ContatosVO>()

        val sql = "SELECT *FROM $TABLE_NAME"
        val cursor :Cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()

        while (cursor.moveToNext()){

            val contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
            )

            lista.add(contato)
        }

        return lista
    }
}