package nl.a3.dora.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.a3.dora.data.data_source.type_converters.GeoTypeConverter
import nl.a3.dora.data.data_source.type_converters.ImgTypeConverter
import nl.a3.dora.data.data_source.type_converters.ListTypeConverter
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route

/**
 * Room Database for local data persistence
 * Singleton pattern for ease of use
 * Database uses SQLite commands for Reading and writing data
 * TODO Database automigration necessary
 */
@Database(
    entities = [
        POI::class,
        Route::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(
    ImgTypeConverter::class,
    GeoTypeConverter::class,
    ListTypeConverter::class
)
abstract class DoraDB : RoomDatabase() {
    abstract fun poiDao(): PoiDao
    abstract fun routeDao(): RouteDao

    companion object {
        private const val DATABASE_NAME = "dora.db"

        @Volatile
        private var INSTANCE: DoraDB? = null

        fun getDBInstance(context: Context): DoraDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            //Synchronized block for instances where parallel threads need the same instance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DoraDB::class.java,
                    DATABASE_NAME,
                )
                    .createFromAsset("database/dora.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}