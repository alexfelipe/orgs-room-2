package br.com.alura.orgs.extensions

import android.widget.ImageView
import br.com.alura.orgs.R
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

fun ImageView.tentaCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    load(url) {
        placeholder(R.drawable.placeholder)
        error(R.drawable.erro)
        fallback(fallback)
    }
}