package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.data.repository.RouteRepositoryImpl
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.RouteViewModel
import nl.a3.dora.data.data_source.RouteDao

/**
 * @brief Used for dependency injection
 * @see RouteDao,
 * @see RouteRepositoryImpl and
 * @see RouteViewModel for injection
 */
interface RouteRepository {
    fun getAllRoutes(): Flow<List<Route>>
    suspend fun getRouteByID(id: Int): Route?
    suspend fun updateRoute(route: Route)
    suspend fun insertRoute(route: Route)
    suspend fun deleteRoute(route: Route)
}