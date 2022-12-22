package nl.a3.dora

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.ui.DORA
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.screens.geofenceDialog
import nl.a3.dora.ui.theme.DORATheme
import nl.a3.dora.viewmodel.RouteViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setupUserLocation(this)
        assignGeofencing()

        val routeViewModel: RouteViewModel by viewModels()

        Companion.routeViewModel = routeViewModel

        setContent {
            DORATheme {
                DORA(routeViewModel)
            }
        }
    }

    companion object {
        var selectedRoute: Route? = null
        var userLocation = GeoPoint(51.5856, 4.7925)
        var lastUserLocation = GeoPoint(0.0,0.0)
        var locationSubscriptions = arrayListOf<(GeoPoint) -> Unit>()
        var geofenceTriggeredPoi = POI(
            -1,
            "default geofence",
            false,
            "",
            "default geofenceTriggeredPoi",
            GeoPoint(51.5856, 4.7925)
        )
        var hasPermissions: Boolean = false

        fun assignGeofencing() {
            locationSubscriptions.add {
                if (selectedRoute != null) {
                    for (poi in selectedRoute?.routeList!!) {
                        val poiLocation = poi.poiLocation

                        if (!poi.isVisited) {
                            if (userLocation.distanceToAsDouble(poiLocation) < 25) {
                                geofenceTriggeredPoi = poi
                                geofenceDialog?.value = 1

                                updateRoute(poi)
                            } else break
                        }
                    }
                }
            }
        }

        var routeViewModel: RouteViewModel? = null
        private fun updateRoute(poi: POI) {
            selectedRoute?.routeList?.forEach {
                if (it.poiID == poi.poiID)
                    it.isVisited = true
            }

            Log.d("UPDATE ROUTE", "${selectedRoute?.routeList}")
            selectedRoute?.let { routeViewModel?.updateType(it) }
        }

        fun setupUserLocation(context: Context) {
            //Setup provider and requests
            val provider = LocationServices.getFusedLocationProviderClient(context)
            val request = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                500L
            ).build()

            //Setup callback
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    //Update locations
                    val location = result.lastLocation
                    if (location != null) {
                        val geoLocation = GeoPoint(location.latitude, location.longitude)
                        userLocation = geoLocation

                        if (userLocation.distanceToAsDouble(lastUserLocation) > 0.5) {
                            lastUserLocation = userLocation

                            //Invoke subscriptions
                            for (subscriber in locationSubscriptions) {
                                subscriber.invoke(geoLocation)
                            }
                        }
                    }
                }
            }

            //Check permissions before assigning request
            val hasFineLocation = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val hasCourseLocation = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasFineLocation && hasCourseLocation
            ) {
                hasPermissions = true

                //Assign request
                provider.requestLocationUpdates(
                    request,
                    callback,
                    Looper.getMainLooper()
                )
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                    99
                )
            }
        }
    }
}