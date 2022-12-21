package nl.a3.dora.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.google.android.gms.location.*
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

    //Get POI List
    val poiList = MainActivity.selectedRoute?.routeList
    if (poiList != null) {
        addRouteToMap(poiList)
        addPOIListToMap(poiList, LocalContext.current)
    }
}