package br.com.alura.orgs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

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
                lifecycleScope.launch {
                    val resultado = dao.autentica(id, senha)?.let {
                        "Usuário autenticado"
                    } ?: "Falha ao autenticar"
                    Toast.makeText(
                        this@LoginActivity,
                        resultado,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            activityLoginBotaoCadastrar.setOnClickListener {
                vaiParaCadastroUsuario()
            }
        }

    }

    private fun vaiParaCadastroUsuario() {
        Intent(this, FormularioCadastroUsuarioActivity::class.java)
            .apply {
                startActivity(this)
            }
    }

}