package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityLoginBinding
import br.com.alura.orgs.extensions.mostraToast
import br.com.alura.orgs.preferences.USUARIO_LOGADO
import br.com.alura.orgs.preferences.dataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            activityLoginBotaoEntrar.setOnClickListener {
                val id = activityLoginUsuario.text.toString()
                val senha = activityLoginSenha.text.toString()
                autentica(id, senha)
            }
            activityLoginBotaoCadastrar.setOnClickListener {
                vaiParaCadastroUsuario()
            }
        }
        lifecycleScope.launch {
            dataStore.data.collect {
                it[USUARIO_LOGADO]?.let { usuario ->
                    Log.i(TAG, "onCreate: $usuario")
                    vaiParaTelaInicial()
                }
            }
        }
    }

    private fun autentica(id: String, senha: String) {
        lifecycleScope.launch {
            try {
                dao.autentica(id, senha)?.let { usuario ->
                    dataStore.edit {
                        it[USUARIO_LOGADO] = usuario.id
                    }
                } ?: mostraToast()
            } catch (e: Exception) {
                Log.e(TAG, "autentica: ", e)
            }
        }
    }

    private fun vaiParaTelaInicial() {
        Intent(this, ListaProdutosActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun vaiParaCadastroUsuario() {
        Intent(this, FormularioCadastroUsuarioActivity::class.java)
            .apply {
                startActivity(this)
            }
    }

}