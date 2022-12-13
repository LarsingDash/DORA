package nl.a3.dora.presentation.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import nl.a3.dora.presentation.ui.component.DialogBox
import nl.a3.dora.viewmodel.PoiViewModel

@Composable
fun HomeScreen(poiViewModel: PoiViewModel) {
    val showDialog = remember {
        mutableStateOf(0)
    }

    val poiListState = poiViewModel.typeListFlow.collectAsState(initial = listOf())

    //TODO Werk dit uit in route nu, plus vul POI table met werkelijke data
    LazyColumn {
        items(poiListState.value.size) { index ->
            val poi = poiListState.value[index]

            Box(modifier = Modifier.padding(5.dp)) {
                Text(text = poi.name)
//                    Text(text = note.content)
            }
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