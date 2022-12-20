package nl.a3.dora.data.repository.test

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.repository.RouteRepository
import org.osmdroid.util.GeoPoint

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
                    POI(0, "Poi 0", true, R.drawable.tower_of_destinity, GeoPoint(51.5856, 4.7925)),
                    POI(1, "Poi 1",false, R.drawable.breda_bieb, GeoPoint(51.58778, 4.78080)),
                    POI(2, "Poi 2",false, R.drawable.breda_stadhuis_nieuw, GeoPoint(51.59461, 4.77896)),
                    POI(3, "Poi 3", true, R.drawable.bocht_of_cingel, GeoPoint(51.5864, 4.7902)), //Geolocation made up
                ),
                thumbnailUri = R.drawable.tower_of_destinity,
                routeLength = 5f,
                routeContent = "This route is used for test data purposes"
            ),
            Route(
                routeID = 1,
                routeName = "Route 2",
                listOf(
//                    POI(poiID = 0, name = "Poi 4", 4f, false, "img1", null),
//                    POI(poiID = 1, name = "Poi 5", 1f, true, "img2", null),
//                    POI(poiID = 2, name = "Poi 6", 19f, true, "img3", null),
//                    POI(poiID = 3, name = "Poi 7", 4f, true, "img4", null),
                ),
                thumbnailUri = R.drawable.breda_bieb,
                routeLength = 10f,
                routeContent = "This second route is used for test data purposes"
            ),
        )
    )

    override fun getAllRoutes(): Flow<List<Route>> {
//        Log.d("DEBUG Route", "Getting all Routes $routeList")
        return routeList.asFlow()
    }

    override suspend fun getRouteByID(id: Int): Route? {
//        Log.d("DEBUG Route", "Getting ${routeList.first()[id]} from list ${routeList.first()}")
        return routeList.first()[id]

    }

    override suspend fun updateRoute(route: Route) {

        Log.d("IMPLEMENTATION Route", "Update needs to be implemented or removed")
        TODO("Needs test implementation")
    }

    override suspend fun insertRoute(route: Route) {
        routeList.first() + route
//        Log.d("DEBUG Route", "Inserting $route to list ${routeList.first()}")
    }

    override suspend fun deleteRoute(route: Route) {
        routeList.first().toMutableList().remove(route)
//        Log.d("DEBUG Route", "Deleting $route from list ${routeList.first()}")
    }
}