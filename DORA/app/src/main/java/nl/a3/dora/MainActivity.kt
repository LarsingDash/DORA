package nl.a3.dora

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.AndroidEntryPoint
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.presentation.ui.DORA
import nl.a3.dora.presentation.ui.theme.DORATheme
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        val poiViewModel: PoiViewModel by viewModels()
        val routeViewModel: RouteViewModel by viewModels()

        //TEST DATA for RoomDB integration
//        val poi = POI(
//            poiID = 0,
//            name = "Oude VVV-pand",
//            distanceTo = 0f,
//            isVisited = false,
//            thumbnailUri = R.drawable.vvv_breda,
//            poiLocation = GeoPoint(51.5941116667, 4.7794166667)
//        )
//
//        poiViewModel.addType(poi)
//
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