package nl.a3.dora

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nl.a3.dora.model.POI
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

        //TODO find a better way to do this
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //TODO same thing
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        val poiViewModel: PoiViewModel by viewModels()
        val routeViewModel: RouteViewModel by viewModels()

        lifecycleScope.launch(Dispatchers.IO) {
            poiViewModel.typeListFlow.first().forEach() {
                Log.d("POI DATA", "$it")
            }
        }
        //TEST DATA for RoomDB integration
        //TODO Fill database with values that represent the necessary data structures
//        val poi = POI(
//            name = "Einde stadswandeling",
//            distanceTo = 0f,
//            isVisited = false,
//            thumbnailUri = R.drawable.einde_route,
//            poiLocation = GeoPoint(51.589780, 4.776203)
//        )
//        poiViewModel.addType(poi)

//        Log of all POI data, for testing usages

//        lifecycleScope.launch {
//            poiViewModel.typeListFlow.first().find { it.poiID == 30 }?.let {
//                poiViewModel.deleteType(it)
//            }
//        }
//        lifecycleScope.launch {
//            val poi = poiViewModel.typeListFlow.first().last().copy(poiID = 26)
//            poiViewModel.updateType(poi)
//        }

//        routeViewModel.addType(
//            Route(
//                routeID = null,
//                routeName = "Route 1",
//                listOf(
//                    poi
//                ),
//                thumbnailUri = R.drawable.tower_of_destinity,
//                routeLength = 20f,
//                routeContent = "Historic tower of awesomeness"
//            )
//        )

        setContent {
            DORATheme {
                DORA(poiViewModel, routeViewModel)
            }
        }
    }
}


//TEST DATA for RoomDB integration
//        val poi = POI(
//            name = "Einde stadswandeling",
//            distanceTo = 0f,
//            isVisited = false,
//            thumbnailUri = R.drawable.einde_route,
//            poiLocation = GeoPoint(51.589780, 4.776203)
//        )
//        poiViewModel.addType(poi)

//        Log of all POI data, for testing usages

//        lifecycleScope.launch {
//            poiViewModel.typeListFlow.first().find { it.poiID == 30 }?.let {
//                poiViewModel.deleteType(it)
//            }
//        }
//        lifecycleScope.launch {
//            val poi = poiViewModel.typeListFlow.first().last().copy(poiID = 26)
//            poiViewModel.updateType(poi)
//        }

//        routeViewModel.addType(
//            Route(
//                routeID = null,
//                routeName = "Route 1",
//                listOf(
//                    poi
//                ),
//                thumbnailUri = R.drawable.tower_of_destinity,
//                routeLength = 20f,
//                routeContent = "Historic tower of awesomeness"
//            )
//        )