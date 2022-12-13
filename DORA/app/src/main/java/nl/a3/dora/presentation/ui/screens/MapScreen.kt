package nl.a3.dora.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.viewmodel.RouteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapScreen (routeViewModel: RouteViewModel) {

    val routeStateList = routeViewModel.typeListFlow.collectAsState(initial = listOf())
    Text(text = "Map")

    Scaffold {
        LazyColumn {
            items(routeStateList.value.size) { index ->
                val route = routeStateList.value[index]
                Text(text = route.routeName,
                    modifier = Modifier.padding(5.dp),
                    lineHeight = 70.sp
                )
                Text(text = route.routeList.toString(),
                    lineHeight = 20.sp
                )
                Text(text = route.routeContent,
                    lineHeight = 70.sp
                )
            }
        }
    }
}