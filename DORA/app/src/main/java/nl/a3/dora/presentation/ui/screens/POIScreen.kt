package nl.a3.dora.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.a3.dora.viewmodel.PoiViewModel

@Composable
fun POIScreen (poiViewModel: PoiViewModel) {
    val poiListState = poiViewModel.typeListFlow.collectAsState(initial = listOf())

    LazyColumn {
        items(poiListState.value.size) { index ->
            val poi = poiListState.value[index]
            Box(modifier = Modifier.padding(5.dp)) {
                Image(
                    painter= painterResource(id = poi.thumbnailUri),
                    contentDescription = poi.name
                )
            }
        }
    }
}