package com.rfelixr.listacontatos.bases

import android.R
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.rfelixr.listacontatos.feature.contato.ContatoActivity


open class BaseActivity : AppCompatActivity(){
    protected fun setupToolBar(toolBar: Toolbar, title: String, navgationBack: Boolean) {
        toolBar.title = title
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(navgationBack)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(com.rfelixr.listacontatos.R.menu.menu_list,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.home -> {
                this.onBackPressed()
                return true
            }
            com.rfelixr.listacontatos.R.id.item_create_contato -> {

                val intent = Intent(this, ContatoActivity::class.java)
                startActivity(intent)

                true
            }

            com.rfelixr.listacontatos.R.id.item_select_contato -> {
                Toast.makeText(this,"Selecionar contatos", Toast.LENGTH_SHORT).show()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}