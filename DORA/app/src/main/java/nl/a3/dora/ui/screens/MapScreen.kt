package nl.a3.dora.ui.screens

import android.location.Location as GPSLocation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import nl.a3.dora.model.POI
import nl.a3.dora.ui.component.OSMMap
import nl.a3.dora.ui.component.addPOIListToMap
import nl.a3.dora.ui.component.addRouteToMap
import org.osmdroid.util.GeoPoint
import androidx.compose.ui.platform.LocalContext
import nl.a3.dora.data.data_source.FusedLocationSource
import kotlinx.coroutines.runBlocking
import nl.a3.dora.data.repository.LocationRepositoryImpl
import nl.a3.dora.model.Location

@Composable
fun MapScreen() {
//    OSMMap {
//        println(it.name)
//    }
//
//    val POIList = arrayLidstOf(
//        TestPOI("Avans", GeoPoint(51.5856, 4.7925)),
//        TestPOI("Station", GeoPoint(51.59461, 4.77896))
//    )
//
//    addPOIListToMap(POIList)
//    addRouteToMap(POIList)

//    val context = LocalContext.current

    val flp = LocationRepositoryImpl(FusedLocationSource(LocalContext.current))

    var text1: Result<Location>? = null

    Thread {
        text1 = runBlocking { flp.getLastLocation() }
    }.start()

    Text(text = "$text1")
}