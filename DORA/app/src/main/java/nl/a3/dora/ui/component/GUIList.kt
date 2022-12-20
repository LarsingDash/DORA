package nl.a3.dora.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.model.Route

@Composable
fun GUIList(routeStateList: State<List<Route>>, modifier: Modifier = Modifier) {
    //generateTestData
    val testList: MutableList<String> = arrayListOf()
    for (i in 1..100) {
        testList.add(i.toString())
    }

    var listIndexRoute: Route? by remember {mutableStateOf(null) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(routeStateList.value.size) {
                index -> val route = routeStateList.value[index]
            if (route == listIndexRoute) {
                FoldedRouteItem(route){listIndexRoute = null}
            } else {
                RouteItem(route){ listIndexRoute = route }
            }
        }
    }
}

@Composable
fun FoldedRouteItem(route: Route, foldAction : () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { foldAction.invoke() }
        ){
            Text(text = route.routeName)
        }
        Text(text = route.routeContent)
    }
}


@Composable
fun RouteItem(route: Route, foldAction : () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { foldAction.invoke() }
    ){
        Row {
            Text(
                text = route.routeName
            )
        }
    }
}