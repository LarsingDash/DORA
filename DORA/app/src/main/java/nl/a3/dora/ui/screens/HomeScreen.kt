package nl.a3.dora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.ui.component.DialogBox
import nl.a3.dora.viewmodel.RouteViewModel


@Composable
fun HomeScreen(routeViewModel: RouteViewModel) {
    val showDialog = remember {
        mutableStateOf(0)
    }

    val routeStateList = routeViewModel.typeListFlow.collectAsState(initial = listOf())

    LazyColumn {
        items(routeStateList.value.size) { index ->
            val route = routeStateList.value[index]
            Text(
                text = route.routeName,
                modifier = Modifier.padding(5.dp),
                lineHeight = 70.sp
            )
            Image(
                painter = painterResource(id = route.thumbnailUri),
                contentDescription = "Cool tower"
            )
            Text(
                text = route.routeContent,
                lineHeight = 70.sp
            )
        }
    }


    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            showDialog.value = 1
        }) {
            Text(text = "Popup Testbutton")
        }
    }

    if (showDialog.value == 1) {
        DialogBox(
            showDialog,
            "TestTitle",
            "Test\nDescription\nDescription\nDescription\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "last",
            mapOf(
//                "No" to { println("no") },
//                "Maybe" to { println("maybe") },
                "Yes" to { println("yes") }
            )
        )
    }
}