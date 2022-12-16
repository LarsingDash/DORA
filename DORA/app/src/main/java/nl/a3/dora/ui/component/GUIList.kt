package nl.a3.dora.ui.component

import android.os.Debug
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun GUIList(modifier: Modifier = Modifier) {
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
                FoldedOutText()
            } else {
                TestText(
                    text = item,
                    index = testList.indexOf(item),
                    {listIndex = testList.indexOf(item)}
                )
            }
        }
    }
}

@Composable
fun FoldedOutText() {
    Card (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "IT WORKED", fontSize = 20.sp)
    }
}


@Composable
fun TestText(text: String, index: Int, buttonClikked : () -> Unit) {
    Card (
        modifier = Modifier.fillMaxWidth()
    ){
        Row {
            Text(
                text = text + "    "
            )
            Button(onClick = buttonClikked) {
                Text(text = "fold out")
            }
            
            Text(text = index.toString())
        }
    }
}


