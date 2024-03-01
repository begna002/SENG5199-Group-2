package com.example.groupassignment2.profile1Content

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.data.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentSearchContent(submitSearch : (String) -> Unit) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var searches = getRecentSearch(context) ?: listOf("")

    if (searches[0] != "") {
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            state = listState
        )
        {
            stickyHeader {
                Row (modifier = Modifier.fillMaxWidth().padding(start = 12.dp)) {
                    RecentSearchHeader("Recent Searches")
                    Text(" (clickable)", fontSize = 10.sp)
                }
                Divider(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 12.dp)
                )
            }
            itemsIndexed(searches) { index, term ->
                RecentSearchItem(term, submitSearch)
                Divider(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp)
                )
            }
        }
    }
}

@Composable
fun RecentSearchItem(term: String, submitSearch : (String) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 32.dp).clickable { submitSearch(term) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Close",
            modifier = Modifier
                .size(20.dp)
        )
        Text(term, modifier = Modifier.padding(start = 12.dp))
    }
}

@Composable
fun RecentSearchHeader(text: String) {
    Text(
        text,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold
    )
}