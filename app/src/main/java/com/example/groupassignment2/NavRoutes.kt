package com.example.groupassignment2

sealed class NavRoutes(val route: String) {
    object Home: NavRoutes("home")
    object Profile1: NavRoutes("Moti")
    object Profile2: NavRoutes("Bassam")
    object Profile3: NavRoutes("profile3")
    object Profile4: NavRoutes("profile4")
    object Profile5: NavRoutes("James (DO NOT ENTER!)")
}