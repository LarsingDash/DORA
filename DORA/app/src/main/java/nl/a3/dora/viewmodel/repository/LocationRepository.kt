package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow
import nl.a3.dora.model.Location
import android.location.Location as GPSLocation

interface LocationRepository {
    suspend fun getLastLocation(): Result<Location>
    suspend fun getCurrentLocation(): Result<Location>
    fun trackCurrentLocation(): Flow<Result<Location>>
    fun trackCurrentGPSLocation(): Flow<Result<GPSLocation>>
}