package com.example.groupassignment2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.groupassignment2.MainViewModel
import java.time.Instant
import java.util.Date


@Composable
fun Profile3(viewModel: MainViewModel) {
    viewModel.profileString = "Rohit visited on ${Date.from(Instant.now())}"
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Profile 3", style = MaterialTheme.typography.headlineSmall)
        }
    }
}
