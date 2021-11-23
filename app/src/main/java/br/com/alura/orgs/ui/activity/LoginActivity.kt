package br.com.alura.orgs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            Intent(this, FormularioCadastroUsuarioActivity::class.java)
                .apply {
                    startActivity(this)
                }
        }
    }

}