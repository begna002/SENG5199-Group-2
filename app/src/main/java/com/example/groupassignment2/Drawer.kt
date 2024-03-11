package com.example.groupassignment2

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun Drawer(navController: NavHostController, drawerContent: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Profiles", modifier = Modifier.padding(16.dp))
                Spacer(modifier = Modifier.height(24.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Home.route) {}
                    }
                )
                Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Moti") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Profile1.route) {}
                    }
                )
                Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Bassam") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Profile2.route) {}
                    }
                )
                Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Rohit") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Profile3.route) {}
                    }
                )
                Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Farshad") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Profile4.route) {}
                    }
                )
                Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "James (DO NOT ENTER)") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                        navController.navigate(NavRoutes.Profile5.route) {}
                    }
                )
            }
        }
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp)
            )
        }

        drawerContent()
    }
}