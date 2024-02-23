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


@Composable
fun Home(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text("Welcome Home!", style = MaterialTheme.typography.headlineSmall)
        }
    }

    Drawer(navController)
}