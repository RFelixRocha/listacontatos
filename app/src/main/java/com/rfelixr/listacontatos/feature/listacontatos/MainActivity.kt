package com.rfelixr.listacontatos.feature.listacontatos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.rfelixr.listacontatos.R
import com.rfelixr.listacontatos.application.ContatoApplication
import com.rfelixr.listacontatos.bases.BaseActivity
import com.rfelixr.listacontatos.feature.contato.ContatoActivity
import com.rfelixr.listacontatos.feature.listacontatos.adapter.ContatoAdapter
import com.rfelixr.listacontatos.feature.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
        initDrawer()
    }

    //drawer menu
    private fun initDrawer(){
        val drawerLayout = findViewById<View>(R.id.drawer_menu) as DrawerLayout

        setSupportActionBar(toolBar)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open_drawer,R.string.close_drawer)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
    }
    //drawer menu

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            var listaFiltrada: List<ContatosVO> = mutableListOf()

            try {
                listaFiltrada = ContatoApplication.instance.helperDB?.buscarContato(busca) ?: mutableListOf()
            }catch (ex: Exception){
                ex.printStackTrace()
            }

            //parte de atualização da tela tem que fica aqui dentro
            runOnUiThread {

                adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()

            }

        }).start()

    }

}