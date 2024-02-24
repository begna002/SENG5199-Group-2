package com.example.groupassignment2.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.groupassignment2.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


private var myList by mutableStateOf(listOf(""))

@Composable
fun Profile1(viewModel: MainViewModel) {
    viewModel.profileString = "Moti visited on ${Date.from(Instant.now())}"

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box (modifier = Modifier
                .padding(top = 48.dp)) {
                Body()
            }
        }
    }
}
@Composable
fun Body() {
    val listState = rememberLazyListState()
    var routine = rememberCoroutineScope()
    val mds = MessageDataSource()

    LaunchedEffect(key1 = Unit){
        routine.launch {
            mds.latestMessage.collect { newMessage ->
                myList += newMessage
            }
        }
    }

//    var lsit = listOf("123", "456","123", "456","123", "456","123", "456","123", "456","123", "456","123", "456","123", "456","123", "456", "hello")

    Column {
        LazyColumn (
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 12.dp, start = 24.dp),
            state = listState
        )
        {
            itemsIndexed(myList) { index, item ->
                LineItem(item)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun LineItem (item: String) {
    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Text(item, style = MaterialTheme.typography.headlineSmall)
    }
}

class MessageDataSource() {
    val latestMessage: Flow<String> = flow {
        for (i in 0 ..< 20) {
            emit("Hello $i")
            delay(10)
        }
    }
}