package br.com.alura.orgs.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProdutosDoUsuario(
    @Embedded val usuario: Usuario,
    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId"
    )
    val produtos: List<Produto>
)