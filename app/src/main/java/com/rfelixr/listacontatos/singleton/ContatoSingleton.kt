package com.rfelixr.listacontatos.singleton

import com.rfelixr.listacontatos.feature.listacontatos.model.ContatosVO

object ContatoSingleton {
    var lista: MutableList<ContatosVO> = mutableListOf()
}