package nl.a3.dora.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.*

var geofenceDialog: MutableState<Int>? = null

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>,
    poiID: Int?
) {
    val poiList = remember {
        mutableStateOf(MainActivity.selectedRoute?.routeList)
    }
    geofenceDialog = remember {
        mutableStateOf(0)
    }

    OSMMap(navController, currentPage)
    Row(Modifier
        .padding(0.dp, 10.dp, 0.dp, 0.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.width(250.dp)
            ,onClick = { recenter(MainActivity.userLocation) }) {
            Text(text = "Recenter")
        }
    }

    //NavArgument
    if (poiID != -1) {
        val poi = poiList.value?.find { it.poiID == poiID }
        recenter(poi!!.poiLocation)
    }

    val context = LocalContext.current
    if (geofenceDialog?.value == 1 && poiList.value != null) {
        addPOIListToMap(poiList.value!!, context)

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
    if (poiList.value != null) {
        addRouteToMap(poiList.value!!)
        addPOIListToMap(poiList.value!!, LocalContext.current)
    }

    updateUserLocation(MainActivity.userLocation, context)
    MainActivity.locationSubscriptions.add {
        updateUserLocation(it, context)

        if (poiList.value != null) {
            addRouteToMap(poiList.value!!)
        }
    }
}