package nl.a3.dora.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.POI

/**
 * @brief Data Access Object (DAO).
 * Used for reading and writing a particular entity from the local database.
 * Reading and writing happens through SQLite with the local database.
 * !See RoomDB documentation for more.
 */
@Dao
interface PoiDao {

    /**
     * @brief Gets all POIs from the poi_table through SQLite queries
     * @return Flow<List<POI>>
     */
    @Query("SELECT * FROM poi_table")
    fun getAllPOIs(): Flow<List<POI>>

    /**
     * @brief Gets a POI from the poi_table through SQLite queries
     */
    @Query("SELECT * FROM poi_table WHERE poiID= :id")
    suspend fun getPOIByID(id: Int): POI?

    /**
     * @brief Updates the value of the selected POI in the Database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePOI(poi: POI)

    /**
     * @brief Inserts a new POI to the database,
     * if this POI has already been added the newest version will take the spot
     */
    @Insert
    suspend fun insertPOI(poi: POI)

    /**
     * @brief Deletes the selected POI from the Database
     */
    @Delete
    suspend fun deletePOI(poi: POI)
}