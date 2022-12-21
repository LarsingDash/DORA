package nl.a3.dora.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.ui.component.OSMMap
import nl.a3.dora.ui.component.addPOIListToMap
import nl.a3.dora.ui.component.addRouteToMap

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>
) {
    OSMMap(navController, currentPage)

    val poiList = MainActivity.selectedRoute?.routeList

    if (poiList != null) {
        println(poiList)
        addPOIListToMap(poiList, LocalContext.current)
        addRouteToMap(poiList)
    }
}