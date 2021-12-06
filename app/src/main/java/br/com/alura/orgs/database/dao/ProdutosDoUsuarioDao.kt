package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.alura.orgs.model.ProdutosDoUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutosDoUsuarioDao {

    @Transaction
    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaProdutosDoUsuario(usuarioId: String): Flow<ProdutosDoUsuario>

}