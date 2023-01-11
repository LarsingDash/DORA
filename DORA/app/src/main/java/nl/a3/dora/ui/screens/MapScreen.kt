package nl.a3.dora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.*
import nl.a3.dora.ui.theme.selectedColor1
import nl.a3.dora.ui.theme.selectedColor2
import nl.a3.dora.ui.theme.unSelectedColor1
import nl.a3.dora.ui.theme.unSelectedColor2

var geofenceDialog: MutableState<Int>? = null

@Composable
fun MapScreen(
    navController: NavController,
    currentPage: MutableState<String>,
    poiID: Int?
) {
    val context = LocalContext.current

    val hasRequestedOnThisPage = remember {
        mutableStateOf(false)
    }
    if (currentPage.value == Pages.Map.title
        && !hasRequestedOnThisPage.value
        && !MainActivity.hasPermissions
    ) {
        hasRequestedOnThisPage.value = true

        MainActivity.setupUserLocation(context)
        MainActivity.assignGeofencing()
    }

    val poiList = remember {
        mutableStateOf(MainActivity.selectedRoute?.routeList)
    }
    geofenceDialog = remember {
        mutableStateOf(0)
    }


    OSMMap(navController, currentPage)
    Row(
        Modifier
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(250.dp)
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(23.dp, 23.dp, 23.dp, 23.dp))
                .clickable { recenter(MainActivity.userLocation, false) }
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            unSelectedColor1,
                            unSelectedColor2,
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.recenter),
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp
            )
        }
    }

    Row(
        Modifier
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ){
        Surface(
            color= Color.DarkGray.copy(alpha = 0.6f),
            modifier = Modifier.padding(7.dp)
        ) {
            Text(text = "Powered by OpenStreetMaps©",
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White
                )
        }
    }

    //NavArgument
    if (poiID != -1) {
        val poi = poiList.value?.find { it.poiID == poiID }
        recenter(poi!!.poiLocation, true)
    }

    if (geofenceDialog?.value == 1 && poiList.value != null) {
        addPOIListToMap(poiList.value!!, context)

        DialogBox(
            showDialog = geofenceDialog!!,
            titleText = context.getString(R.string.geofence_title),
            description = context.getString(R.string.geofence_desc),
            buttons = mapOf(
                context.getString(R.string.close) to Pair(false) {},
                context.getString(R.string.read_more) to Pair(true) {
                    navController.navigate(Pages.POI.title + "/${MainActivity.geofenceTriggeredPoi.poiID}")
                    currentPage.value = Pages.POI.title
                }
            )
        )
    }

//  Get POI List
    if (poiList.value != null) {
        addRouteToMap(poiList.value!!)
        addPOIListToMap(poiList.value!!, LocalContext.current)
    }

    updateUserLocation(MainActivity.userLocation, context)
    MainActivity.locationSubscriptions.add {
        updateUserLocation(it, context)

        if (poiList.value != null) {
            addRouteToMap(poiList.value!!)
        }
    }
}