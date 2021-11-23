package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.launch

private const val TAG = "CadastroUsuario"

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        with(binding) {
            activityFormularioCadastroBotaoCadastrar.setOnClickListener {
                val novoUsuario = criaUsuario()
                lifecycleScope.launch {
                    salva(novoUsuario)
                }
            }
        }
    }

    private fun criaUsuario(): Usuario {
        with(binding) {
            val id = activityFormularioCadastroUsuario.text.toString()
            val nome = activityFormularioCadastroNome.text.toString()
            val senha = activityFormularioCadastroSenha.text.toString()
            return Usuario(id, nome, senha)
        }
    }

    private suspend fun salva(usuario: Usuario) {
        try {
            dao.salva(usuario)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "salva: ", e)
            Toast.makeText(
                this@FormularioCadastroUsuarioActivity,
                "Ocorreu um problema no cadastro",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}