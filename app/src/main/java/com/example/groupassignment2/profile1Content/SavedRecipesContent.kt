package com.example.groupassignment2.profile1Content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.groupassignment2.data.FoodItem

private var foodResponse by mutableStateOf(listOf(FoodItem()))

val clearResponse: () -> Unit = {
    foodResponse = listOf(FoodItem())
}

@Composable
fun SavedRecipesContent() {
    val context = LocalContext.current
    val gridState = rememberLazyGridState()

    // Need a better way to clear (save to mutable state)

    foodResponse = getSavedRecipes(context) ?: listOf(FoodItem())
    if (foodResponse[0].id != 0L) {
        // Add Grid
        Column(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 12.dp, bottom = 24.dp)
            ) {
                RemoveAllButton()
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                state = gridState
            ) {
                itemsIndexed(foodResponse) { index, item ->
                    LineItem(item, true)
                }
            }
        }
    } else {
        // Some Message about no saved recipes
    }
}

@Composable
fun RemoveAllButton() {
    val context = LocalContext.current

    Button(
        enabled = true,
        onClick = {
            deleteAll(context, clearResponse)
        },
        modifier = Modifier.size(120.dp, 50.dp),
    ) {
        Text(
            text = "Remove All"
        )
    }
}

@Composable
fun RemoveRecipeButton(item: FoodItem, closeAfterDelete: () -> Unit) {
    val context = LocalContext.current
    var startWrite by remember { mutableStateOf(false) }

    if (startWrite) {
        deleteRecipe(context, item, clearResponse)
        startWrite = false
        closeAfterDelete()
    }
    TextButton(
        enabled = true,
        onClick = {
            startWrite = true
        },
    ) {
        Text(
            text = "Remove"
        )
    }
}