package nl.a3.dora.data.repository

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.data.data_source.RouteDao
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.repository.RouteRepository

/**
 * @brief implementation class of RouteRepository
 * @see RouteRepository
 */
class RouteRepositoryImpl(
    private val routeDao: RouteDao
): RouteRepository {
    override fun getAllRoutes(): Flow<List<Route>> = routeDao.getAllRoutes()
    override suspend fun getRouteByID(id: Int): Route? = routeDao.getRouteByID(id)
    override suspend fun updateRoute(route: Route) = routeDao.updateRoute(route)
    override suspend fun insertRoute(route: Route) = routeDao.insertRoute(route)
    override suspend fun deleteRoute(route: Route) = routeDao.deleteRoute(route)
}