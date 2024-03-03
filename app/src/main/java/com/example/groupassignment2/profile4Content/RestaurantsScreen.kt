package com.example.groupassignment2.profile4Content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun RestaurantsScreen() {
    val viewModel: RestaurantsViewModel = viewModel()

    //val state: MutableState<List<Restaurant>> =remember { mutableStateOf(viewModel.getRestaurants())}
//    LaunchedEffect(key1 = "request_restaurants") {
//        viewModel.getRestaurants()
//    }
    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Text(
                text = "14 Recommended Restaurants",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(80, 80, 200)
            )
        }
        LazyColumn (
            contentPadding = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp)) {
            items(viewModel.state.value) { restaurant ->
                RestaurantItem(restaurant) { id->
                    viewModel.toggleFavorite(id)
                }
            }
        }
        Spacer(modifier = Modifier.padding(30.dp))


    }

}


@Composable
fun RestaurantItem(item: Restaurant,
                   onClick: (id:Int) -> Unit) {
//    var favoriteState by remember { mutableStateOf(false)}

    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(elevation= CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.weight(0.15f))
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.weight(0.70f))
            RestaurantIcon(icon, Modifier.weight(0.15f))
            {onClick(item.id)
            }
        }
    }
}

@Composable
private fun RestaurantIcon(icon: ImageVector,
                           modifier: Modifier,
                           onClick: () -> Unit={}) {

    Image(imageVector = icon,
        contentDescription = "Restaurant icon" ,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}

@Composable
fun RestaurantDetails(title: String, description: String,
                      modifier: Modifier) {

    Column (modifier = modifier) {
        Text(text = title,
            style = MaterialTheme.typography.headlineMedium)

        Text(text = description,
            style = MaterialTheme.typography.bodyMedium)

    }
}
