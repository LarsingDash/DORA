package nl.a3.dora

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nl.a3.dora.model.Route
import nl.a3.dora.ui.DORA
import nl.a3.dora.ui.theme.DORATheme
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO find a better way to do this
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //TODO same thing
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        val poiViewModel: PoiViewModel by viewModels()
        val routeViewModel: RouteViewModel by viewModels()

        lifecycleScope.launch(Dispatchers.IO) {
            poiViewModel.typeListFlow.first().forEach() {
                Log.d("POI DATA", "$it")
            }
        }

        setupUserLocation()

        setContent {
            DORATheme {
                DORA(poiViewModel, routeViewModel)
            }
        }
    }

    private fun setupUserLocation() {
        //Setup provider and requests
        val provider = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            250L
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

                    //Invoke subscriptions
                    for (subscriber in locationSubscriptions) {
                        subscriber.invoke(geoLocation)
                    }
                }
            }
        }

        //Check permissions before assigning request
        val hasFineLocation = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCourseLocation = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation && hasCourseLocation
        ) {
            //Assign request
            provider.requestLocationUpdates(
                request,
                callback,
                Looper.getMainLooper()
            )
        } else Log.println(Log.DEBUG, "DEBUG", "No permissions")
    }


//TEST DATA for RoomDB integration
//        val poi = POI(
//            name = "Einde stadswandeling",
//            distanceTo = 0f,
//            isVisited = false,
//            thumbnailUri = R.drawable.einde_route,
//            poiLocation = GeoPoint(51.589780, 4.776203)
//        )
//        poiViewModel.addType(poi)

//        Log of all POI data, for testing usages

//        lifecycleScope.launch {
//            poiViewModel.typeListFlow.first().find { it.poiID == 30 }?.let {
//                poiViewModel.deleteType(it)
//            }
//        }
//        lifecycleScope.launch {
//            val poi = poiViewModel.typeListFlow.first().last().copy(poiID = 26)
//            poiViewModel.updateType(poi)
//        }

//        routeViewModel.addType(
//            Route(
//                routeID = null,
//                routeName = "Route 1",
//                listOf(
//                    poi
//                ),
//                thumbnailUri = R.drawable.tower_of_destinity,
//                routeLength = 20f,
//                routeContent = "Historic tower of awesomeness"
//            )
//        )

    companion object {
        var selectedRoute: Route? = null
        var userLocation = GeoPoint(0.0, 0.0)
        var locationSubscriptions = arrayListOf<(GeoPoint) -> Unit>()
    }
}