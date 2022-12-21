package nl.a3.dora.data.data_source

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nl.a3.dora.data.repository.LocationClientImpl
import nl.a3.dora.viewmodel.repository.LocationClient
import org.osmdroid.util.GeoPoint

class LocationService(
    private val context: Context
) : Service() {

    private val geoPoint = GeoPoint(0.0,0.0,0.0)

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = LocationClientImpl(
            context,
            LocationServices.getFusedLocationProviderClient(context),
            this.geoPoint,
        )
        start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ServiceCast")
    fun start() {
        locationClient.getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                this.geoPoint.latitude = location.latitude
                this.geoPoint.longitude = location.longitude
            }
            .launchIn(serviceScope)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    fun getLastLocation():GeoPoint {
        return locationClient.getLocation()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}