package nl.a3.dora.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FusedLocationSource(
    context: Context,
) {
    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        request: CurrentLocationRequest,
    ): Result<Location> = suspendCoroutine { e ->
        locationProviderClient.getCurrentLocation(request, null)
            .addOnSuccessListener {
                if (it !== null) e.resume(Result.success(it))
                else e.resume(Result.failure(Exception("Location not found")))
            }
            .addOnFailureListener { e.resume(Result.failure(it)) }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation():
            Result<Location> = suspendCoroutine { e ->
        locationProviderClient.lastLocation
            .addOnSuccessListener {
                if (it !== null) e.resume(Result.success(it))
                else e.resume(Result.failure(Exception("Location not found")))
            }
            .addOnFailureListener {
                e.resume(Result.failure(it))
            }
    }

    @SuppressLint("MissingPermission")
    fun trackLocation(
        request: LocationRequest,
    ): Flow<Result<Location>> = callbackFlow {
        val listener = LocationListener { location -> trySend(Result.success(location)) }

        locationProviderClient.requestLocationUpdates(request, listener, null)

        awaitClose { locationProviderClient.removeLocationUpdates(listener) }
    }.catch { emit(Result.failure(it)) }
}