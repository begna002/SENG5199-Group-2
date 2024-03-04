package com.example.groupassignment2.profile4Content

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun RestaurantsScreen() {
    val viewModel: RestaurantsViewModel = viewModel()

    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Text(
                text = "Twin Cities' 'Must Visit' Restaurants",
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
                { URlText(url = item.uri, text = "Website: ${item.title}" ) },
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
                      urlContent: @Composable () -> Unit,
                      modifier: Modifier) {
    Column (modifier = modifier) {
        Text(text = title,
            style = MaterialTheme.typography.headlineMedium)

        Text(text = description,
            style = MaterialTheme.typography.bodyMedium)
        urlContent()

    }
}

@Composable
fun URlText(url: String, text: String) {
    val context = LocalContext.current

    val annotatedString = AnnotatedString.Builder(text). apply {
        addStyle(
            style = SpanStyle(
                color = Color.Magenta,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            ),
            start = 0,
             end = text.length
        )
    }. toAnnotatedString()

    ClickableText(
        text = annotatedString ,
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    )
}


