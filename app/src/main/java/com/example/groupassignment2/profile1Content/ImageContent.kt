package com.example.groupassignment2.profile1Content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.groupassignment2.R
import com.example.groupassignment2.data.FoodItem

@Composable
fun ImageContent(item: FoodItem, imageModifier: Modifier, dietModifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .memoryCacheKey(item.imageUrl)
                .diskCacheKey(item.imageUrl)
                .build()
        ),
        contentDescription = item.title,
        modifier = imageModifier
    )
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.Bottom
    ){
       DietContent(item, dietModifier)
    }
}

@Composable
fun DietContent(item: FoodItem, dietModifier: Modifier) {
    if (item.isVegetarian) {
        Image(
            painter = painterResource(id = R.drawable.vegetarian),
            contentDescription = item.title,
            modifier = dietModifier
        )
    }
    if (item.isVegan) {
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            painter = painterResource(id = R.drawable.vegan),
            contentDescription = item.title,
            modifier = dietModifier
        )
    }
}