package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("SELECT id FROM Usuario WHERE id = :id LIMIT 1")
    suspend fun buscaPorId(id: String): String?

    @Query("SELECT * FROM Usuario WHERE id = :id AND senha = :senha")
    suspend fun autentica(id: String, senha: String): Usuario?

}