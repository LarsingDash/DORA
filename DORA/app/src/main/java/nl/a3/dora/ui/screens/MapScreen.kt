package nl.a3.dora.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import nl.a3.dora.model.POI
import nl.a3.dora.ui.component.OSMMap
import nl.a3.dora.ui.component.addPOIListToMap
import nl.a3.dora.ui.component.addRouteToMap
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>
) {
    OSMMap(navController, currentPage)

    val POIList = arrayListOf(
        POI(0, "Avans", true, 0, GeoPoint(51.5856, 4.7925)),
        POI(1, "Casino", true, 0, GeoPoint(51.58778, 4.78080)),
        POI(2, "Station", false, 0, GeoPoint(51.59461, 4.77896))
    )

    addPOIListToMap(POIList)
    addRouteToMap(POIList)
}