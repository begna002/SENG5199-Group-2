package com.example.groupassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groupassignment2.screens.Home
import com.example.groupassignment2.screens.Profile1
import com.example.groupassignment2.screens.Profile2
import com.example.groupassignment2.screens.Profile3
import com.example.groupassignment2.screens.Profile4
import com.example.groupassignment2.screens.Profile5
import com.example.groupassignment2.ui.theme.GroupAssignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupAssignment2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            Home(navController = navController)
        }

        composable(NavRoutes.Profile1.route) {
            Profile1(navController = navController)
        }

        composable(NavRoutes.Profile2.route) {
            Profile2(navController = navController)
        }

        composable(NavRoutes.Profile3.route) {
            Profile3(navController = navController)
        }

        composable(NavRoutes.Profile4.route) {
            Profile4(navController = navController)
        }

        composable(NavRoutes.Profile5.route) {
            Profile5(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupAssignment2Theme {
        MainScreen()
    }
}