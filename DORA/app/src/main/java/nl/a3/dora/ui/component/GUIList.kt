package nl.a3.dora.ui.component

<<<<<<< HEAD
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
=======
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
>>>>>>> feature-homescreen
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GUIList(modifier: Modifier = Modifier) {
    //generateTestData
=======
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun GUIList(modifier: Modifier = Modifier) {
>>>>>>> feature-homescreen
    val testList: MutableList<String> = arrayListOf()
    for (i in 1..100) {
        testList.add(i.toString())
    }
<<<<<<< HEAD

=======
>>>>>>> feature-homescreen
    var listIndex by remember {mutableStateOf(-1) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(testList) {
                item ->
            if (listIndex == testList.indexOf(item)) {
<<<<<<< HEAD
                FoldedOutText(text = item,){listIndex = -1}
            } else {
                TestText(text = item,){listIndex = testList.indexOf(item)}
=======
                FoldedOutText()
            } else {
                TestText(
                    text = item,
                    index = testList.indexOf(item),
                    {listIndex = testList.indexOf(item)}
                )
>>>>>>> feature-homescreen
            }
        }
    }
}

@Composable
<<<<<<< HEAD
fun FoldedOutText(text: String, foldAction : () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { foldAction.invoke() }
        ){
            Text(text = text)
        }
=======
fun FoldedOutText() {
    Card (
        modifier = Modifier.fillMaxWidth()
    ){
>>>>>>> feature-homescreen
        Text(text = "IT WORKED", fontSize = 20.sp)
    }
}


@Composable
<<<<<<< HEAD
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
=======
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
>>>>>>> feature-homescreen
        }
    }
}


