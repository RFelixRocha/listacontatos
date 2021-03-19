package com.rfelixr.listacontatos.feature.contato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rfelixr.listacontatos.R
import com.rfelixr.listacontatos.application.ContatoApplication
import com.rfelixr.listacontatos.feature.listacontatos.model.ContatosVO
import com.rfelixr.listacontatos.singleton.ContatoSingleton
import com.rfelixr.listacontatos.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_contato.*
import kotlinx.android.synthetic.main.activity_contato.toolBar

class ContatoActivity : BaseActivity() {

    private var idContato: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, "Contato",true)
        setupContato()
        btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato(){

        progress.visibility = View.VISIBLE

        idContato = intent.getIntExtra("index",-1)

        if (idContato == -1){
            btnExcluirContato.visibility = View.GONE
            return
        }


        Thread(Runnable {
            Thread.sleep(2000)
            var lista = ContatoApplication.instance.helperDB?.buscarContato("$idContato",true) ?: return@Runnable

            val contato = lista?.getOrNull(0) ?: return@Runnable

            runOnUiThread{
                etNome.setText(contato.nome)
                etTelefone.setText(contato.telefone)
                progress.visibility = View.GONE
            }

        }).start()

    }

    private fun onClickSalvarContato(){

        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        progress.visibility = View.VISIBLE

        Thread(Runnable {

            if(idContato == -1) {
                ContatoApplication.instance.helperDB?.insertContato(contato)
            }else{
                ContatoApplication.instance.helperDB?.updateContato(contato)
            }

            runOnUiThread{
                progress.visibility = View.GONE
                finish()
            }

        }).start()

    }

    fun onClickExcluirContato(view: View) {

        if(idContato > -1){

            progress.visibility = View.VISIBLE

            Thread(Runnable {

                ContatoApplication.instance.helperDB?.deleteContato(idContato)

                runOnUiThread{
                    progress.visibility = View.GONE
                    finish()
                }

            }).start()


        }
    }
}