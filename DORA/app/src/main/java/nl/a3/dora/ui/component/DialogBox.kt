package nl.a3.dora.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.a3.dora.ui.theme.confirmButton

@Composable
fun DialogBox(
    showDialog: MutableState<Int>,
    titleText: String,
    description: String,
    buttons: Map<String, Pair<Boolean, () -> Unit>>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0, 0, 0, 0x44)
    ) {}

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(
            modifier = Modifier
                .padding(35.dp),
            shape = RoundedCornerShape(25.dp),
        ) {
            Column {
                val innerPadding = 15.dp

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding, 10.dp),
                    text = titleText,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .padding(innerPadding, 0.dp)
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 400.dp)
                        .verticalScroll(ScrollState(0)),
                    text = description,
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier.padding(innerPadding / 2, innerPadding)
                ) {
                    for (set in buttons) {
                        var buttonColor = ButtonDefaults.buttonColors(Color.LightGray)
                        var textColor = Color.DarkGray

                        if (set.value.first) {
                            buttonColor = ButtonDefaults.buttonColors(confirmButton)
                            textColor = Color.White
                        }

                        Button(
                            modifier = Modifier
                                .padding(innerPadding / 2, 0.dp)
                                .weight(1 / buttons.size.toFloat())
                                .clip(RoundedCornerShape(25.dp)),
                            onClick = {
                                showDialog.value = 0
                                set.value.second.invoke()
                            },
                            colors = buttonColor,
                            shape = RectangleShape,
                        ) {

                            Text(
                                text = set.key,
                                style = MaterialTheme.typography.body1,
                                color = textColor,
                            )
                        }
                    }
                }
            }
        }
    }
}