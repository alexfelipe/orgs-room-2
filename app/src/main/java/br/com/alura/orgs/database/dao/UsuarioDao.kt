package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun buscaPorId(id: String): Usuario?

}