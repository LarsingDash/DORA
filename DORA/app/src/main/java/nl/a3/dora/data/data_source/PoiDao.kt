package nl.a3.dora.data.data_source

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.POI

/**
 * @brief Data Access Object (DAO)
 * used for reading and writing a particular entity from the local database
 * !See RoomDB documentation for more
 * Reading and writing happens through SQLite with the local database
 */
@Dao
interface PoiDao {

    @Query("SELECT * FROM poi_table")
    fun getAllPOIs(): Flow<List<POI>>


}