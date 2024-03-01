package com.example.groupassignment2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.MainViewModel
import com.example.groupassignment2.profile1Content.RecipeSearchContent
import com.example.groupassignment2.profile1Content.SavedRecipesContent
import java.time.Instant
import java.util.Date


@Composable
fun Profile1(viewModel: MainViewModel) {
    viewModel.profileString = "Moti visited on ${Date.from(Instant.now())}"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text("Recipe Hub",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0, 104, 143)
                )
        }
        Column{
            Box(
                modifier = Modifier
                    .padding(top = 48.dp)
            ) {
                TabScreen()
            }
        }
    }
}

@Composable
fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Recipe Search", "Saved Recipes", "Explore")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> RecipeSearch()
            1 -> SavedRecipes()
            2 -> ExploreRecipes()
        }
    }
}


@Composable
fun RecipeSearch() {
    RecipeSearchContent()
}

@Composable
fun SavedRecipes() {
    SavedRecipesContent()
}

@Composable
fun ExploreRecipes() {
}
