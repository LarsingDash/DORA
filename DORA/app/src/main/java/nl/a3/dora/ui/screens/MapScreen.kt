package nl.a3.dora.ui.screens

import androidx.compose.runtime.Composable
import nl.a3.dora.TestPOI
import nl.a3.dora.ui.component.OSMMap
import nl.a3.dora.ui.component.addPOIListToMap
import nl.a3.dora.ui.component.addRouteToMap
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen() {
    OSMMap {
        println(it.name)
    }

    val POIList = arrayListOf(
        TestPOI("Avans", GeoPoint(51.5856, 4.7925)),
        TestPOI("Station", GeoPoint(51.59461, 4.77896))
    )

    addPOIListToMap(POIList)
    addRouteToMap(POIList)
}