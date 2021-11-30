package br.com.alura.orgs.migrations

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.migrations.MIGRATION_1_2
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class MigrationsTest {

    private val TEST_DB = "orgs-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migracao1Para2() {
        //Abrir o banco de dados numa versão específica
        val db = helper.createDatabase(TEST_DB, 1)

        //Inserir alguns dados no banco de dados
        val produto = ContentValues().apply {
            put("id", 1)
            put("nome", "teste1")
            put("descricao", "teste2")
            put("valor", 2.0)
        }
        db.insert("Produto", OnConflictStrategy.ABORT, produto)

        //Rodar as migrations e verificar se o schema está correto
        val dbMigrado = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            TEST_DB
        ).addMigrations(MIGRATION_1_2)
            .build()

        //Realizar novas ações após migração
        val usuario = ContentValues().apply {
            put("id", "teste")
            put("nome", "teste1")
            put("senha", "teste2")
        }
        db.insert("Usuario", OnConflictStrategy.ABORT, usuario)

        //Verificar se o dado inserido no banco de dados é o esperado
        runBlocking {
            val produtoEncontrado = dbMigrado.produtoDao()
                .buscaPorId(1).first()
            assertEquals(
                Produto(
                    1,
                    "teste1",
                    descricao = "teste2",
                    valor = BigDecimal("2.0")
                ), produtoEncontrado
            )
            val usuarioEncontrado = dbMigrado.usuarioDao().buscaPorId("teste")
            assertEquals(
                Usuario(
                    "teste",
                    "teste1",
                    "teste2"
                ),
                usuarioEncontrado
            )
        }
    }

}