package nl.a3.dora.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.Route

/**
 * @brief Data Access Object for reading and writing Route data from the database.
 * Reading and writing goes through SQLite injection
 */
@Dao
interface RouteDao {

    /**
     * @brief Gets all Routes from the route_table through SQLite queries
     * @return Flow<List<Route>>
     */
    @Query("SELECT * FROM route_table")
    fun getAllRoutes(): Flow<List<Route>>

    /**
     * @brief Gets a Route from the route_table through SQLite queries
     */
    @Query("SELECT * FROM route_table WHERE routeID= :id")
    suspend fun getRouteByID(id: Int): Route?

    /**
     * @brief Updates the value of the selected Route in the Database
     */
    @Update
    suspend fun updateRoute(route: Route)

    /**
     * @brief Inserts a new Route to the database,
     */
    @Insert
    suspend fun insertRoute(route: Route)

    /**
     * @brief Deletes the selected Route from the Database
     */
    @Delete
    suspend fun deleteRoute(route: Route)
}