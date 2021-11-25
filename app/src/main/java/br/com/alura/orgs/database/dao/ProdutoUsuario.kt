package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoUsuarioDao {

    @Transaction
    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun buscaProdutosDoUsuario(id: String): Flow<ProdutoUsuario>

}

data class ProdutoUsuario(
    @Embedded val usuario: Usuario,
    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId"
    )
    val produtos: List<Produto>
)