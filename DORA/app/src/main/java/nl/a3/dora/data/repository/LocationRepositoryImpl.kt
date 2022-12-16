package nl.a3.dora.data.repository

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.Flow
import android.location.Location as GPSLocation
import kotlinx.coroutines.flow.map
import nl.a3.dora.data.data_source.FusedLocationSource
import nl.a3.dora.model.Location
import nl.a3.dora.viewmodel.repository.LocationRepository

class LocationRepositoryImpl(
    private val datasource: FusedLocationSource
): LocationRepository {
    override suspend fun getLastLocation() = datasource.getLastLocation().map { it.toDomain() }

    override suspend fun getCurrentLocation() = datasource.getCurrentLocation(
        CurrentLocationRequest.Builder()
            .setDurationMillis(10_000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setGranularity(Granularity.GRANULARITY_FINE)
            .setMaxUpdateAgeMillis(1_000)
            .build()
    ).map { it.toDomain() }

    override fun trackCurrentGPSLocation(): Flow<Result<GPSLocation>> = datasource.trackLocation(
        LocationRequest.Builder(500)
            .setGranularity(Granularity.GRANULARITY_FINE)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setMaxUpdateAgeMillis(LocationRequest.Builder.IMPLICIT_MAX_UPDATE_AGE)
            .setMinUpdateIntervalMillis(LocationRequest.Builder.IMPLICIT_MIN_UPDATE_INTERVAL)
            .build()
    )

    override fun trackCurrentLocation(): Flow<Result<Location>> = trackCurrentGPSLocation()
        .map { result -> result.map { it.toDomain() } }
}

fun GPSLocation.toDomain() = Location(
    latitude = this.latitude,
    longitude = this.longitude
)