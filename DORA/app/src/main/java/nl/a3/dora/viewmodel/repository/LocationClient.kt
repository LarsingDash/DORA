package nl.a3.dora.viewmodel.repository

import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<android.location.Location>

    class LocationException(message: String): Exception()
}