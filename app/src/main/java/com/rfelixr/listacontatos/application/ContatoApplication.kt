package com.rfelixr.listacontatos.application

import android.app.Application
import com.rfelixr.listacontatos.helpers.HelperDB

class ContatoApplication: Application(){

    var helperDB: HelperDB? = null
        private set

    override fun onCreate() {
        super.onCreate()

        helperDB = HelperDB(this)
    }
}