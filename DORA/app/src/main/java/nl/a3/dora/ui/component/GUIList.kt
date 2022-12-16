package nl.a3.dora.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GUIList(modifier: Modifier = Modifier) {
    //generateTestData
    val testList: MutableList<String> = arrayListOf()
    for (i in 1..100) {
        testList.add(i.toString())
    }

    var listIndex by remember {mutableStateOf(-1) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(testList) {
                item ->
            if (listIndex == testList.indexOf(item)) {
                FoldedOutText(text = item,){listIndex = -1}
            } else {
                TestText(text = item,){listIndex = testList.indexOf(item)}
            }
        }
    }
}

@Composable
fun FoldedOutText(text: String, foldAction : () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { foldAction.invoke() }
        ){
            Text(text = text)
        }
        Text(text = "IT WORKED", fontSize = 20.sp)
    }
}


@Composable
fun TestText(text: String, foldAction : () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { foldAction.invoke() }
    ){
        Row {
            Text(
                text = text
            )
        }
    }
}


