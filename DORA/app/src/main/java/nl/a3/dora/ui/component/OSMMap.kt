package nl.a3.dora.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.ui.Pages
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.IconOverlay
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline

private lateinit var mapView: MapView
private lateinit var poiOverlay: ItemizedIconOverlay<POIOverlayItem>
private var locationOverlay: IconOverlay = IconOverlay()
private lateinit var roadManager: RoadManager
private var routeOverlays = arrayListOf<Polyline>()

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
                minZoomLevel = 15.0
                maxZoomLevel = 20.0
                isTilesScaledToDpi = true

                controller.setCenter(MainActivity.userLocation)
                controller.setZoom(17.0)

                mapView.overlays.add(poiOverlay)
//                mapView.zoomController.display.setBitmaps()
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
        ItemizedIconOverlay(
            mutableListOf<POIOverlayItem>(),
            context.getDrawable(R.drawable.unvisited),
            listener,
            context
        )
    }
}

//Other Functions
fun addPOIListToMap(
    POIList: List<POI>,
    context: Context,
) {
    poiOverlay.removeAllItems()

    for (poi in POIList) {
        poiOverlay.addItem(
            POIOverlayItem(poi, context.getString(context.resources.getIdentifier(poi.poiName, "string", context.packageName)))
        )

        if (poi.isVisited) {
            poiOverlay.getItem(poiOverlay.size() - 1)
                .setMarker(context.getDrawable(R.drawable.visited))
        }
    }

    mapView.invalidate()
}

fun addRouteToMap(originalPOIList: List<POI>) {
    mapView.overlays.removeAll(routeOverlays)
    routeOverlays.clear()

    //Add userPOI to route
    val poiList = arrayListOf<POI>()
    poiList.addAll(originalPOIList)
    for (poi in originalPOIList) {
        if (!poi.isVisited) {
            poiList.add(
                poiList.indexOf(poi),
                POI(
                    -1,
                    "user",
                    true,
                    "",
                    "location of user",
                    MainActivity.userLocation))
            break
        }
    }

    //Make subRoutes between all POI's
    val loaders = arrayListOf<Thread>()
    for (poi in poiList) {
        //Skip if index is last element
        val index = poiList.indexOf(poi)
        if (index == poiList.size - 1) continue

        //Get nextPOI and make subRoute
        val nextPOI = poiList[index + 1]
        val subRoute: ArrayList<GeoPoint> = arrayListOf(poi.poiLocation, nextPOI.poiLocation)

        //Create routeOverlay using OpenSourceRoadManager
        val loader = Thread {
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
            mapView.overlays.add(0, routeOverlay)
            routeOverlays.add(routeOverlay)
        }
        loader.start()
        loaders.add(loader)
    }

    loaders.forEach { it.join() }

    //Update map
    mapView.invalidate()
}

fun updateUserLocation(geoPoint: GeoPoint, context: Context) {
    mapView.overlays.remove(locationOverlay)
    locationOverlay = IconOverlay(geoPoint, context.getDrawable(R.drawable.user))
    mapView.overlays.add(locationOverlay)
    mapView.invalidate()
}

fun recenter(geoPoint: GeoPoint, isInstant: Boolean) {
    val speed = if (isInstant) 0L else 1250L
    mapView.controller.animateTo(geoPoint, 18.0, speed)
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