package nl.a3.dora

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
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

//        val historicKM: List<POI> = listOf(
//            poiViewModel.getTypeByID(26),
//            poiViewModel.getTypeByID(19),
//            poiViewModel.getTypeByID(27),
//            poiViewModel.getTypeByID(28),
//        ) as List<POI>
//
//        routeViewModel.addType(
//            Route(
//                routeName = "de_route",
//                routeList = historicKM,
//                thumbnailUri = "pad_van_sinterklaas",
//                routeLength = 69f,
//                routeDescription = "route_description_de_route"
//            )
//        )
//        lifecycleScope.launch {
//            routeViewModel.getTypeByID(2)?.let { routeViewModel.deleteType(it) }
//        }

//        lifecycleScope.launch(Dispatchers.IO) {
//            val poi = poiViewModel.typeListFlow.first().find { it.poiName == R.string.grote_kerk }?.copy(poiLocation = GeoPoint(51.588784341353744, 4.775163473055657))
//            poi?.let { poiViewModel.updateType(it) }
//        }

        setContent {
            DORATheme {
                DORA(poiViewModel, routeViewModel)
            }
        }
    }

    companion object {
        var selectedRoute: Route? = null
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

//        val deROUTE: List<POI> = listOf(
//            poiViewModel.getTypeByID(27),
//            poiViewModel.getTypeByID(19),
//            poiViewModel.getTypeByID(28),
//            poiViewModel.getTypeByID(26)
//        ) as List<POI>

//        lifecycleScope.launch(Dispatchers.IO) {
//            poiViewModel.typeListFlow.first().forEach {
//                Log.d("POI DATA", "$it")
//            }
//        }

//        lifecycleScope.launch(Dispatchers.IO) {
//            routeViewModel.typeListFlow.first().forEach {
//                Log.d("ROUTE DATA", "$it")
//            }
//        }

