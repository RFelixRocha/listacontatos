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

    fun buscarContato(busca: String, isBuscaId: Boolean = false) : List<ContatosVO>{

        val db :SQLiteDatabase = readableDatabase ?: return mutableListOf()
        val lista :MutableList<ContatosVO> = mutableListOf<ContatosVO>()
        var where: String? = null
        var args: Array<String> = arrayOf()

        if (isBuscaId){
            where = "$COLUMNS_ID = ?"
            args = arrayOf("$busca")
        }else{
            where = "$COLUMNS_NOME LIKE ? OR $COLUMNS_TELEFONE LIKE ?"
            args = arrayOf("%$busca%","%$busca%")
        }

        val cursor :Cursor = db.query(TABLE_NAME,null,where,args,null,null,COLUMNS_NOME)

        if (cursor == null){
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){

            val contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
            )

            lista.add(contato)
        }

        db.close()

        return lista
    }

    fun insertContato(contato: ContatosVO){

        val db :SQLiteDatabase = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUMNS_NOME,$COLUMNS_TELEFONE) VALUES(?,?)"
        val array :Array<String> = arrayOf(contato.nome,contato.telefone)

        db.execSQL(sql, array)
        db.close()
    }

    fun deleteContato(id: Int){
        val db = writableDatabase ?: return
        val where = "$COLUMNS_ID = ?"
        val args = arrayOf("$id")

        db.delete(TABLE_NAME,where,args)
        db.close()
    }
}