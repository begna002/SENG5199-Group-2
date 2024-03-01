package com.example.groupassignment2.profile1Content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.R
import com.example.groupassignment2.data.FoodItem
import com.example.groupassignment2.data.FoodResponse
import kotlinx.coroutines.launch

private var responseFetched by mutableStateOf(false)
private var searchable by mutableStateOf(false)
private var query by mutableStateOf("")
private var foodResponse by mutableStateOf(FoodResponse())


val submitSearch: (String) -> Unit = {text ->
    query = text
    searchable = true
    responseFetched = false
    foodResponse = FoodResponse()
}

@Composable
fun RecipeSearchContent() {
    SearchBar()
    if (searchable) {
        Body()
    } else {
        RecentSearchContent(submitSearch)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar() {
    var routine = rememberCoroutineScope()
    val apiKey = stringResource(R.string.foodApiKey);
    val keyboardController = LocalSoftwareKeyboardController.current
    val saveSearch = remember { mutableStateOf(false) }


    when {
        saveSearch.value -> {
            SaveRecentSearch(query)
            saveSearch.value = false
        }
    }

    Column(
        modifier = Modifier.padding(start = 12.dp, top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = { if (it.length <= 50) query = it },
                singleLine = true,
                placeholder = {Text("Find Recipes")},
                modifier = Modifier
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key != Key.Enter) return@onKeyEvent false
                        if (keyEvent.type == KeyEventType.KeyUp) {
                            searchable = true
                            responseFetched = false
                            saveSearch.value = true
                            foodResponse = FoodResponse()
                            keyboardController?.hide()
                        }
                        true

                    }
                    .requiredWidth(290.dp)
            )
            Button(
                onClick = {
                    searchable = true
                    responseFetched = false
                    saveSearch.value = true
                    foodResponse = FoodResponse()
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .size(100.dp, 50.dp)
                    .padding(start = 12.dp),
            ) {
                Text(
                    text = "Search",
                    fontSize = 12.sp,
                )
            }
        }
    }

    if (!responseFetched && searchable) {
        LaunchedEffect(key1 = Unit) {
            routine.launch {
                foodResponse = getFoodData(apiKey, query)
                responseFetched = true
            }
        }
    }
}

@Composable
fun Body() {
    val gridState = rememberLazyGridState()

    if (foodResponse.results.size > 1) {
        Column(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            ClearSearchButton()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                state = gridState
            ) {
                itemsIndexed(foodResponse.results) { index, item ->
                    LineItem(item, false)
                }
            }
        }
    } else if (foodResponse.error) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Error Fetching Data: ${foodResponse.message}",
                fontSize = 24.sp
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            CircularProgressIndicator(
                Modifier
                    .size(100.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                strokeWidth = 18.dp
            )
        }
    }
}

@Composable
fun LineItem(item: FoodItem, isSaved: Boolean) {
    val startDialog = remember { mutableStateOf(false) }

    when {
        startDialog.value -> {
            RecipeDetailsDialog(startDialog, item, isSaved)
        }
    }

    Column(
        modifier = Modifier
            .clickable {
                startDialog.value = true
            }
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item.title,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp))
        Box(
            modifier = Modifier.size(width = 156.dp, height = 115.dp)
        ) {
            ImageContent(
                item,
                Modifier.size(width = 156.dp, height = 115.dp),
                Modifier.size(width = 35.dp, height = 35.dp)
            )
        }
    }
}

@Composable
fun SaveRecipeButton(item: FoodItem) {
    val context = LocalContext.current
    var startWrite by remember { mutableStateOf(false) }
    val startDialog = remember { mutableStateOf(false) }
    var dialogText = remember { mutableStateOf("") }
    var isDuplicate = remember { mutableStateOf(false) }

    if (startWrite) {
        startDialog.value = true
        var savedRecipes = getSavedRecipes(context)
        var savedRecipesArray: ArrayList<FoodItem>?

        if (savedRecipes != null) {
            savedRecipesArray = ArrayList(savedRecipes)
        } else {
            savedRecipesArray = null
        }

        savedRecipes?.forEach { recipe ->
            run {
                if (recipe.id == item.id) {
                    isDuplicate.value = true
                }
            }
        }
        if (isDuplicate.value) {
            dialogText.value = "Recipe Already Saved";
        } else {
            isDuplicate.value = false
            dialogText.value = "Saved Recipe!";
            SaveRecipe(context, item, savedRecipesArray)
        }
        startWrite = false
    }

    when {
        startDialog.value -> {
            SaveRecipeDialog(startDialog, dialogText.value)
        }
    }

    TextButton(
        enabled = true,
        onClick = {
            startWrite = true
        },
    ) {
        Text(
            text = "Save"
        )
    }
}

@Composable
fun ClearSearchButton() {
    Row (
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                searchable = false
                foodResponse = FoodResponse()
                query = ""
            },
            modifier = Modifier
                .size(140.dp, 50.dp)
                .padding(start = 12.dp),
        ) {
            Text(
                text = "Clear Search",
                fontSize = 12.sp,
            )
        }
    }
}