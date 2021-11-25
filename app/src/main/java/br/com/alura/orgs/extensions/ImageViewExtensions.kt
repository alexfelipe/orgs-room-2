package br.com.alura.orgs.extensions

import android.widget.ImageView
import br.com.alura.orgs.R
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

fun ImageView.tentaCarregarImagemArredondada(
    url: Int,
    fallback: Int = R.drawable.imagem_padrao
) {
    tentaCarregarImagem(url, fallback) {
        transformations(
            CircleCropTransformation(),
        )
    }
}

fun ImageView.tentaCarregarImagemArredondada(
    url: String?,
    fallback: Int = R.drawable.imagem_padrao
) {
    tentaCarregarImagem(url, fallback) {
        transformations(
            CircleCropTransformation(),
        )
    }
}

fun ImageView.tentaCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    load(url) {
        imageBuildPadrao(fallback = fallback, builder)
    }
}

fun ImageView.tentaCarregarImagem(
    url: Int,
    fallback: Int = R.drawable.imagem_padrao,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    load(url) {
        imageBuildPadrao(fallback = fallback, builder)
    }
}

private fun ImageRequest.Builder.imageBuildPadrao(
    fallback: Int,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    builder()
    crossfade(true)
    placeholder(R.drawable.placeholder)
    error(R.drawable.erro)
    fallback(fallback)
}