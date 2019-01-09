package br.com.arduino.home.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.arduino.home.R
import br.com.arduino.home.adapter.ListaRowAdapter
import br.com.arduino.home.bean.RowBean
import br.com.arduino.home.dao.RowsDAO
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var rowsDAO = RowsDAO(null)
    var adapter = ListaRowAdapter(null, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rowsDAO = RowsDAO(this)


        val rows = rowsDAO.getRows()
        adapter = ListaRowAdapter(rows, this, this.rowsDAO, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyler.layoutManager = mLayoutManager
        recyler.itemAnimator = DefaultItemAnimator()
        recyler.adapter = adapter

    }

    fun atualizaLista() {
        adapter.mRows = rowsDAO.getRows()
        adapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cadastrar -> {
                showCreateCategoryDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    fun showCreateCategoryDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("")


        val view = layoutInflater.inflate(R.layout.nova_url, null)
        builder.setView(view)

        val dialog = builder.create()

        val edUrl = view.findViewById(R.id.url_text) as EditText
        val edDescricao = view.findViewById(R.id.descricao_text) as EditText
        val btnSalvar = view.findViewById(R.id.btn_salvar) as Button
        val btnCancelar = view.findViewById(R.id.btn_cancelar) as Button


        btnSalvar.setOnClickListener {
            var isValid = true

            if (edUrl.text.isEmpty()) {
                edUrl.error = "Campo Obrigatório"
                isValid = false
            } else if (edDescricao.text.isEmpty()) {
                edDescricao.error = "Campo Obrigatório"
                isValid = false
            }

            if (isValid) {

                val insert = rowsDAO.insert(RowBean(0, edUrl.text.toString(), edDescricao.text.toString(), "0"))
                if (insert) {
                    Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                    atualizaLista()
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "ERROR !", Toast.LENGTH_SHORT).show()
                }
            }
        }


        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
}
