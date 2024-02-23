package com.example.groupassignment2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import com.example.groupassignment2.Drawer
import com.example.groupassignment2.MainViewModel


@Composable
fun Profile4(navController: NavHostController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text("Profile 4", style = MaterialTheme.typography.headlineSmall)
        }
    }
    Drawer(navController)
}
