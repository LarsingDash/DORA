package nl.a3.dora.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import nl.a3.dora.viewmodel.repository.LocationClient
import org.osmdroid.util.GeoPoint

@Suppress("DEPRECATION")
class LocationClientImpl(
    private val context: Context,
    private val client: FusedLocationProviderClient,
    private var geoPoint: GeoPoint,
) : LocationClient {
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationClient.LocationException()
            }

            val request = LocationRequest.create()
                .setInterval(interval)
                .setFastestInterval(interval)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult.locations.lastOrNull()?.let { location ->
                        if (locationResult.lastLocation != null) {
                            geoPoint.latitude = locationResult.lastLocation!!.latitude
                            geoPoint.longitude = locationResult.lastLocation!!.longitude
                        }
                        launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper(),
            )

            awaitClose{
                client.removeLocationUpdates(locationCallback)
            }

        }
    }

    override fun getLocation(): GeoPoint{
        return this.geoPoint
    }
}