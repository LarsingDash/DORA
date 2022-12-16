package nl.a3.dora.data.repository.test

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.repository.RouteRepository

/**
 * @brief test class for communication model usage
 */
class DummyRepoRoute: RouteRepository {

    private val routeList: List<List<Route>> = listOf(
        listOf(
            Route(
                routeID = 0,
                routeName = "Route 1",
                listOf(
                    POI(0, "Poi 0", 4f, true, 1, null),
                    POI(1, "Poi 1", 1f, false, 2, null),
                    POI(2, "Poi 2", 19f, false, 3, null),
                    POI(3, "Poi 3", 4f, true, 4, null),
                ),
                thumbnailUri = 0,
                routeLength = 5f,
                routeContent = "This route is used for test data purposes"
            ),
            Route(
                routeID = 1,
                routeName = "Route 2",
                listOf(
                    POI(0, "Poi 4", 4f, false, 1, null),
                    POI(1, "Poi 5", 1f, true, 2, null),
                    POI(2, "Poi 6", 19f, true, 3, null),
                    POI(3, "Poi 7", 4f, true, 4, null),
                ),
                thumbnailUri = 0,
                routeLength = 10f,
                routeContent = "This second route is used for test data purposes"
            ),
        )
    )

    override fun getAllRoutes(): Flow<List<Route>> {
        Log.d("DEBUG Route", "Getting all Routes $routeList")
        return routeList.asFlow()
    }

    override suspend fun getRouteByID(id: Int): Route? {
        Log.d("DEBUG Route", "Getting ${routeList.first()[id]} from list ${routeList.first()}")
        return routeList.first()[id]

    }

    override suspend fun updateRoute(route: Route) {

        Log.d("IMPLEMENTATION Route", "Update needs to be implemented or removed")
        TODO("Needs test implementation")
    }

    override suspend fun insertRoute(route: Route) {
        routeList.first() + route
        Log.d("DEBUG Route", "Inserting $route to list ${routeList.first()}")
    }

    override suspend fun deleteRoute(route: Route) {
        routeList.first().toMutableList().remove(route)
        Log.d("DEBUG Route", "Deleting $route from list ${routeList.first()}")
    }
}