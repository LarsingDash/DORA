package nl.a3.dora.ui.component

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.runBlocking
import nl.a3.dora.TestPOI
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline

private lateinit var mapView: MapView
private lateinit var poiOverlay: ItemizedIconOverlay<POIOverlayItem>
private lateinit var routeOverlay: Polyline
private lateinit var roadManager: RoadManager

//Main Composable
@Composable
fun OSMMap(
    onPOIClicked: (TestPOI) -> Unit,
) {
    //Initiate variables
    val context = LocalContext.current
    val updatedOnPOIClicked by rememberUpdatedState(onPOIClicked)

    mapView = remember { MapView(context) }
    roadManager = OSRMRoadManager(context, Configuration.getInstance().userAgentValue)
    (roadManager as OSRMRoadManager).setMean(OSRMRoadManager.MEAN_BY_FOOT)

    //Create overlays
    poiOverlay = createPOIOverlay(
        context = context,
        updatedOnPOIClicked = updatedOnPOIClicked
    )

    routeOverlay = createRouteOverlay(
        mapView = mapView
    )

    //Create map
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            mapView.apply {
                minZoomLevel = 5.0
                maxZoomLevel = 20.0
                isTilesScaledToDpi = true

                controller.setCenter(GeoPoint(51.5856, 4.7925))
                controller.setZoom(17.0)

                mapView.overlays.add(poiOverlay)
                mapView.overlays.add(routeOverlay)
            }
        },
    )

    MapLifecycle(mapView = mapView)
}

//Components
@Composable
private fun createPOIOverlay(
    context: Context,
    updatedOnPOIClicked: (TestPOI) -> Unit,
): ItemizedIconOverlay<POIOverlayItem> {
    return remember {
        //Create listener from clicks on the POI
        val listener = object : ItemizedIconOverlay.OnItemGestureListener<POIOverlayItem> {
            //Single tap
            override fun onItemSingleTapUp(index: Int, item: POIOverlayItem?): Boolean {
                item?.poi?.let(updatedOnPOIClicked)
                return true
            }

            //Hold (not in use)
            override fun onItemLongPress(index: Int, item: POIOverlayItem?): Boolean {
                return false
            }
        }

        //Create and return overlay
        ItemizedIconOverlay(context, mutableListOf<POIOverlayItem>(), listener)
    }
}

@Composable
private fun createRouteOverlay(
    mapView: MapView
): Polyline {
    return remember {
        Polyline(mapView, true, true)
    }
}

//Other Functions
fun addPOIListToMap(POIList: List<TestPOI>) {
    poiOverlay.removeAllItems()
    poiOverlay.addItems(
        POIList.map { POIOverlayItem(it) }
    )
    mapView.invalidate()
}

fun addRouteToMap(POIList: List<TestPOI>) {
    mapView.overlays.remove(routeOverlay)

    val route: ArrayList<GeoPoint> = arrayListOf()
    for (poi in POIList) {
        route.add(poi.geoPoint)
    }

    runBlocking {
        val road = roadManager.getRoad(route)
        routeOverlay = RoadManager.buildRoadOverlay(road)
    }

    mapView.overlays.add(routeOverlay)
    mapView.invalidate()
}

@Composable
private fun MapLifecycle(mapView: MapView) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(mapView) {
        val mapLifecycleObserver = mapView.lifecycleObserver()

        lifecycle.addObserver(mapLifecycleObserver)

        onDispose {
            lifecycle.removeObserver(mapLifecycleObserver)
        }
    }
}

private fun MapView.lifecycleObserver() = LifecycleEventObserver { _, event ->
    when (event) {
        Lifecycle.Event.ON_RESUME -> this.onResume()
        Lifecycle.Event.ON_PAUSE -> this.onPause()
        else -> {}
    }
}

private class POIOverlayItem(
    val poi: TestPOI
) : OverlayItem(poi.name, null, poi.geoPoint)