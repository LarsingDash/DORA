package nl.a3.dora.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.ui.component.OSMMap
import nl.a3.dora.ui.component.updateUserLocation
import nl.a3.dora.ui.component.addPOIListToMap
import nl.a3.dora.ui.component.addRouteToMap
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>
) {
    OSMMap(navController, currentPage)

    //Get POI List
//    val poiList = MainActivity.selectedRoute?.routeList
    val poiList = arrayListOf(
        POI(0, R.string.avans_hogeschool, true, 0, 0, GeoPoint(51.5856, 4.7925)),
        POI(1, R.string.kloosterkazerne, true, 0, 0, GeoPoint(51.58778, 4.78080)),
        POI(2, R.string.station_breda, false, 0, 0, GeoPoint(51.59461, 4.77896))
    )
    if (poiList != null) {
        addRouteToMap(poiList)
        addPOIListToMap(poiList, LocalContext.current)
    }

    val context = LocalContext.current
    updateUserLocation(MainActivity.userLocation, context)
    MainActivity.locationSubscriptions.add {
        updateUserLocation(it, context)
        addRouteToMap(poiList)
    }
}