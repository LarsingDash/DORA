package nl.a3.dora.viewmodel.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLastLocation(): Result<Location>
    suspend fun getCurrentLocation(): Result<Location>
    fun trackCurrentLocation(): Flow<Result<Location>>
    fun trackCurrentGPSLocation(): Flow<Result<Location>>
}