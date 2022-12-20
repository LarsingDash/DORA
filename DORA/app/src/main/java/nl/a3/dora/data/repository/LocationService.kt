package nl.a3.dora.data.repository

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nl.a3.dora.R
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
            LocationServices.getFusedLocationProviderClient(context)
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
//        val notification = NotificationCompat.Builder(this, "location")
//            .setContentTitle("Tracking location...")
//            .setContentText("Location: null")
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setOngoing(true)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        locationClient.getLocationUpdates(5000L)
//            .catch { e -> e.printStackTrace() }
//            .onEach { location ->
//                val lat = location.latitude.toString()
//                val long = location.longitude.toString()
//                val updatedNotification = notification.setContentText(
//                    "location: ($lat, $long)"
//                )
//                notificationManager.notify(1, updatedNotification.build())
//            }
//            .launchIn(serviceScope)
//
//        startForeground(1, notification.build())
//
        println("bruh")
        locationClient.getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                println("poopoo")
                val lat = location.latitude
                val long = location.longitude
                this.geoPoint.latitude = lat
                this.geoPoint.longitude = long
            }
            .launchIn(serviceScope)
        println("bruh2")
    }

    fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    fun getLastLocation():GeoPoint {
        println("Peepeepoopoo")
        return this.geoPoint
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}