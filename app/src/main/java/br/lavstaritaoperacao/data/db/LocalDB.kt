package br.lavstaritaoperacao.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.lavstaritaoperacao.data.db.dao.ItemDao
import br.lavstaritaoperacao.data.db.dao.ServiceDao
import br.lavstaritaoperacao.data.db.entities.ItemEntity
import br.lavstaritaoperacao.data.db.entities.ServiceEntity

@Database(
    entities = [
        ItemEntity::class,
        ServiceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDB: RoomDatabase() {

    abstract fun itemDao() : ItemDao
    abstract fun serviceDao() : ServiceDao


    companion object{

        private const val DATABASE_NAME = "LocalDB"

        fun createDatabase(context: Context): LocalDB{
            return Room.databaseBuilder(
                context.applicationContext,
                LocalDB::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()

        }
    }

}