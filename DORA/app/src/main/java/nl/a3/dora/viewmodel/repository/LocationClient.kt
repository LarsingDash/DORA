package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow
import org.osmdroid.util.GeoPoint

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<android.location.Location>
    fun getLocation(): GeoPoint

    class LocationException : Exception()
}