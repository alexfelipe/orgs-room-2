package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }
    private var usuarioLogado: Usuario? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            buscaProdutos()
            tentaBuscarUsuario()
            usuarioLogado?.apply {
                Log.i("ListaProdutos", "onCreate: $usuarioLogado")
            } ?: finish()
        }
    }

    private suspend fun tentaBuscarUsuario() {
        usuarioLogado = intent.getStringExtra(CHAVE_USUARIO_ID)?.let { usuario ->
            usuarioDao.buscaPorId(usuario)
        }
    }

    private fun CoroutineScope.buscaProdutos() {
        launch {
            dao.buscaTodos().collect { produtos ->
                adapter.atualiza(produtos)
            }
        }
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