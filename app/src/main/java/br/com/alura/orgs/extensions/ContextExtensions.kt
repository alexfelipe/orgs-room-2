package br.com.alura.orgs.extensions

import android.content.Context
import android.widget.Toast

fun Context.mostraToast() {
    Toast.makeText(
        this,
        "Falha na autenticação",
        Toast.LENGTH_SHORT
    ).show()
}