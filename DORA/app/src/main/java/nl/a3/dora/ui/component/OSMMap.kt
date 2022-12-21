package nl.a3.dora.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import nl.a3.dora.DoraApp
import nl.a3.dora.model.POI
import nl.a3.dora.ui.Pages
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

private lateinit var mapView: MapView
private lateinit var poiOverlay: ItemizedIconOverlay<POIOverlayItem>
private lateinit var roadManager: RoadManager

//Main Composable
@Composable
fun OSMMap(
    navController: NavController,
    currentPage: MutableState<String>,
) {
    //Initiate variables
    val context = LocalContext.current

    mapView = remember { MapView(context) }

    roadManager = OSRMRoadManager(context, Configuration.getInstance().userAgentValue)
    (roadManager as OSRMRoadManager).setMean(OSRMRoadManager.MEAN_BY_FOOT)

    //Create overlays
    poiOverlay = createPOIOverlay(
        context = context,
        navController,
        currentPage,
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
            }
        },
    )

    MapLifecycle(mapView = mapView)
}

//Components
@Composable
private fun createPOIOverlay(
    context: Context,
    navController: NavController,
    currentPage: MutableState<String>,
): ItemizedIconOverlay<POIOverlayItem> {
    return remember {
        //Create listener from clicks on the POI
        val listener = object : ItemizedIconOverlay.OnItemGestureListener<POIOverlayItem> {
            //Single tap
            override fun onItemSingleTapUp(index: Int, item: POIOverlayItem?): Boolean {
                item?.poi?.let {
                    navController.navigate(Pages.POI.title + "/${it.poiID}")
                    currentPage.value = Pages.POI.title
                }
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

//Other Functions

fun addPOIListToMap(POIList: List<POI>, context: Context) {
    poiOverlay.addItems(
        POIList.map { POIOverlayItem(it, context
            .getString(
                it
                    .poiName)) }
    )
    mapView.invalidate()
}

fun addRouteToMap(POIList: List<POI>) {
    //Make subRoutes between all POI's
    for (POI in POIList) {
        //Skip if index is last element
        val index = POIList.indexOf(POI)
        if (index == POIList.size - 1) continue

        //Get nextPOI and make subRoute
        val nextPOI = POIList[index + 1]
        val subRoute: ArrayList<GeoPoint> = arrayListOf(POI.poiLocation, nextPOI.poiLocation)

        //Create routeOverlay using OpenSourceRoadManager
        Thread {
            //Creation
            val road = roadManager.getRoad(subRoute)
            val routeOverlay = RoadManager.buildRoadOverlay(road)

            //Design
            routeOverlay.outlinePaint.strokeCap = Paint.Cap.ROUND
            routeOverlay.outlinePaint.strokeWidth = 15f
            routeOverlay.outlinePaint.strokeJoin = Paint.Join.ROUND

            //Change color if the POI has been visited already
            if (nextPOI.isVisited) {
                routeOverlay.outlinePaint.color = Color.GREEN
            } else {
                routeOverlay.outlinePaint.color = Color.BLUE
            }

            //Add the overlay to all overlays
            mapView.overlays.add(routeOverlay)
        }.start()
    }

    //Update map
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
    val poi: POI,
    poiName: String
) : OverlayItem(poiName, null, poi.poiLocation)