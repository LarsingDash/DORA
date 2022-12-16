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
import nl.a3.dora.R.color.*
import nl.a3.dora.ui.theme.confirmButton

@Composable
fun DialogBox(
    showDialog: MutableState<Int>,
    titleText: String,
    description: String,
    buttons: Map<String, Pair<Boolean, () -> Unit>>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(15.dp, 0.dp)
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.LightGray,
            ) {
                Text(
                    modifier = Modifier
                        .heightIn(min = 100.dp, max = 400.dp)
                        .verticalScroll(ScrollState(0)),
                    text = description,
                    textAlign = TextAlign.Center,
                )
            }

            Row {
                for (set in buttons) {
                    var buttonColor = ButtonDefaults.buttonColors(Color.LightGray)
                    var textColor = Color.DarkGray

                    if (set.value.first) {
                        buttonColor = ButtonDefaults.buttonColors(confirmButton)
                        textColor = Color.White
                    }

                    Button(
                        modifier = Modifier
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