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
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
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

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setupUserLocation()

        val poiViewModel: PoiViewModel by viewModels()
        val routeViewModel: RouteViewModel by viewModels()

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
            Priority.PRIORITY_HIGH_ACCURACY,
            1000L
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

                    if (
                        Math.abs(userLocation.latitude - lastUserLocation.latitude) > 0.00001
                        || Math.abs(userLocation.longitude - lastUserLocation.longitude) > 0.00001
                    ) {
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

    companion object {
        var selectedRoute: Route? = null
        var userLocation = GeoPoint(51.5856, 4.7925)
        var lastUserLocation = userLocation
        var locationSubscriptions = arrayListOf<(GeoPoint) -> Unit>()
    }
}