package nl.a3.dora

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
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

        //TEST DATA for RoomDB integration
        //TODO Fill database with values that represent the necessary data structures
        val poi = POI(
            name = "Bibliotheek",
            distanceTo = 0f,
            isVisited = false,
            thumbnailUri = R.drawable.breda_bieb,
            poiLocation = GeoPoint(51.587638, 4.779667)
        )
        poiViewModel.addType(poi)

//        Log of all POI data, for testing usages

//        lifecycleScope.launch {
//            poiViewModel.typeListFlow.first().find { it.poiID == 11 }?.let {
//                poiViewModel.deleteType(it)
//            }
//        }
//        lifecycleScope.launch {
//            val poi = poiViewModel.typeListFlow.first()[10].copy(thumbnailUri = R.drawable.grote_kerk)
//            poiViewModel.updateType(poi)
//
//            val poi1 = poiViewModel.typeListFlow.first()[11].copy(thumbnailUri = R.drawable.ridder_straat)
//            poiViewModel.updateType(poi1)
//
//            val poi2 = poiViewModel.typeListFlow.first()[12].copy(thumbnailUri = R.drawable.grotemarkt)
//            poiViewModel.updateType(poi2)
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