package nl.a3.dora.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nl.a3.dora.viewmodel.PoiViewModel

@Composable
fun POIScreen(poiViewModel: PoiViewModel) {
    val poiListState = poiViewModel.typeListFlow.collectAsState(initial = listOf())


    poiListState.value.forEach{
        Log.d("DEBUG DATA", "POI DATA: $it")
    }


    LazyColumn {
        items(poiListState.value.size) { index ->
            val poi = poiListState.value[index]
            Text(
                text = poi.name,
                modifier = Modifier.padding(5.dp),
                lineHeight = 70.sp
            )
            Image(
                painter = painterResource(id = poi.thumbnailUri),
                contentDescription = "Cool tower"
            )
            Text(
                text = "Me when coding this fucking shit",
                lineHeight = 70.sp
            )
            Text(
                text = "Dogukan 14-12-2022",
                fontStyle = FontStyle.Italic,
                lineHeight = 70.sp
            )
        }
    }
}