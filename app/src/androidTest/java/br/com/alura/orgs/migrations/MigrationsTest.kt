package br.com.alura.orgs.migrations

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.alura.orgs.database.AppDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        //Inserir alguns dados no banco de dados
        //Rodar as migrations e verificar se o schema está correto
        //Realizar novas ações após migração
        //Verificar se o dado inserido no banco de dados é o esperado
    }

}