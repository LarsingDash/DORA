package nl.a3.dora.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.ktor.http.HttpMethod.Companion.Get
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

//    Get POI List
    val poiList = MainActivity.selectedRoute?.routeList
    if (poiList != null) {
        addRouteToMap(poiList)
        addPOIListToMap(poiList, LocalContext.current)
    }

    val context = LocalContext.current
    updateUserLocation(MainActivity.userLocation, context)
    MainActivity.locationSubscriptions.add {
        updateUserLocation(it, context)

        if (poiList != null) {
            addRouteToMap(poiList)
        }
    }
}