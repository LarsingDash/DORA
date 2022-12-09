@file:OptIn(ExperimentalMaterialApi::class)

package nl.a3.dora.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DialogBox(
    showDialog: MutableState<Int>,
    titleText: String,
    description: String,
    buttons: Map<String, () -> Unit>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            color = Color.LightGray,
        ) {
            Column {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Gray,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 10.dp),
                        text = titleText,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(0.dp, 10.dp),
                    text = description,
                    textAlign = TextAlign.Center,
                )

                Row {
                    for (set in buttons) {
                        Button(
                            onClick = {
                                showDialog.value = 0
                                set.value.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            Text(text = set.key)
                        }
                    }
                }
            }
        }
    }
}