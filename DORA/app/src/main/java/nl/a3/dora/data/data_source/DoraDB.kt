package nl.a3.dora.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.a3.dora.model.POI

//TODO Database automigration necessary
/**
 * Room Database for local data persistence
 * Singleton pattern for use of ease
 * Database uses SQLite commands for Reading and writing data
 */
@Database(entities = [POI::class], version = 1, exportSchema = false)
abstract class DoraDB: RoomDatabase() {
    abstract fun poiDao(): PoiDao

    companion object {
        private const val DATABASE_NAME = "dora_db"
        @Volatile
        private var INSTANCE: DoraDB? = null

        fun getDBInstance(context: Context): DoraDB {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            //Synchronized block for instances where parallel threads need the same instance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DoraDB::class.java,
                    DATABASE_NAME,
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}