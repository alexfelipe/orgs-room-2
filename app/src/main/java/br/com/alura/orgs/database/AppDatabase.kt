package br.com.alura.orgs.database

import android.content.Context
import androidx.room.*
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.database.dao.ProdutosDoUsuarioDao
import br.com.alura.orgs.database.dao.UsuarioDao
import br.com.alura.orgs.database.migrations.MIGRATION_1_2
import br.com.alura.orgs.database.migrations.MIGRATION_2_3
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Usuario

@Database(
    entities = [
        Produto::class,
        Usuario::class
    ],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 3,
            to = 4
        )
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao

    abstract fun usuarioDao(): UsuarioDao

    abstract fun produtosDoUsuarioDao(): ProdutosDoUsuarioDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3
            ).fallbackToDestructiveMigrationOnDowngrade()
                .build().also {
                    db = it
                }
        }
    }
}