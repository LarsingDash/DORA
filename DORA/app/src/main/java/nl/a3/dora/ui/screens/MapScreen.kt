package nl.a3.dora.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.*

var geofenceDialog: MutableState<Int>? = null

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>
) {
    geofenceDialog = remember {
        mutableStateOf(0)
    }

    OSMMap(navController, currentPage)
    Button(onClick = { recenter() }) {
        Text(text = "Recenter")
    }

    val context = LocalContext.current
    if (geofenceDialog?.value == 1) {
        DialogBox(
            showDialog = geofenceDialog!!,
            titleText = context.getString(R.string.geofence_title),
            description = context.getString(R.string.geofence_desc),
            buttons = mapOf(
                context.getString(R.string.close) to Pair(false, {}),
                context.getString(R.string.read_more) to Pair(true) {
                    navController.navigate(Pages.POI.title + "/${MainActivity.geofenceTriggeredPoi.poiID}")
                    currentPage.value = Pages.POI.title
                }
            )
        )
    }

//  Get POI List
    val poiList = MainActivity.selectedRoute?.routeList
    if (poiList != null) {
        addRouteToMap(poiList)
        addPOIListToMap(poiList, LocalContext.current)
    }

    updateUserLocation(MainActivity.userLocation, context)
    MainActivity.locationSubscriptions.add {
        updateUserLocation(it, context)

        if (poiList != null) {
            addRouteToMap(poiList)
        }
    }
}