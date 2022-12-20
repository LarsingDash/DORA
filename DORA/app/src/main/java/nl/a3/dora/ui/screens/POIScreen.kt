package nl.a3.dora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.MainActivity
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.viewmodel.PoiViewModel

@Composable
fun POIScreen(
    poiID: Int?,
) {
    val nullableList by remember {
        mutableStateOf(MainActivity.selectedRoute?.routeList)
    }

//    Text(text = "$poiID")
//    val poiListState = poiViewModel.typeListFlow.collectAsState(initial = listOf())

    val list: List<POI> = nullableList ?: listOf()

    LazyColumn {
        items(list.size) { index ->
            val poi = list[index]
            Text(
                text = poi.name,
                modifier = Modifier.padding(5.dp),
                lineHeight = 70.sp
            )
            Image(
                painter = painterResource(id = poi.thumbnailUri),
                contentDescription = "Cool tower"
            )
            if(poi.poiID == poiID) {
                Text(
                    text = "Je moeder ofzo: $poiID",
                    fontStyle = FontStyle.Italic,
                    lineHeight = 70.sp
                )
            }
        }
    }
}