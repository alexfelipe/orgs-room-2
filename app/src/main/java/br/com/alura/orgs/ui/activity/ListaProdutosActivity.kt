package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.preferences.USUARIO_LOGADO
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }
    private val produtoUsuarioDao by lazy {
        AppDatabase.instancia(this).produtoUsuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            launch {
                verificaUsuarioLogado()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lista_produtos_sair -> {
                lifecycleScope.launch {
                    desloga()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect {
            it[USUARIO_LOGADO]?.let { usuario ->
                usuarioDao.buscaPorId(usuario)?.also { usuarioEncontrado ->
                    title = usuarioEncontrado.nome
                    buscaProdutosUsuario(usuarioEncontrado.id)
                }
            } ?: desloga()
        }
    }

    private suspend fun buscaProdutosUsuario(usuarioId: String) {
        produtoUsuarioDao
            .buscaProdutosDoUsuario(usuarioId)
            .map { produtosUsuario ->
                produtosUsuario.produtos
            }
            .collect(adapter::atualiza)
    }

    private suspend fun desloga() = dataStore.edit {
        it.remove(USUARIO_LOGADO)
        finish()
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

}