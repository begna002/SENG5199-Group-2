package com.example.groupassignment2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.groupassignment2.MainViewModel
import com.example.groupassignment2.profile4Content.RestaurantsScreen
import java.time.Instant
import java.util.Date


@Composable
fun Profile4(viewModel: MainViewModel) {
    viewModel.profileString = "You last visited Farshad's page with a " +
            "with a list of his recommended restaurants on" +
            " ${Date.from(Instant.now())}"
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RestaurantsScreen()
        }
    }
