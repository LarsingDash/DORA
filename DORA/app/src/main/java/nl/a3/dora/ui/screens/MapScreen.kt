package nl.a3.dora.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.ui.component.*

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>
) {
    OSMMap(navController, currentPage)
    Button(onClick = { recenter() }) {
        Text(text = "Recenter")
    }

//  Get POI List
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