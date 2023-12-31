package com.example.materialhass.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Devices
import androidx.compose.material.icons.rounded.Room
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.materialhass.model.MainScreenDest
import com.example.materialhass.screens.DevicesScreen
import com.example.materialhass.screens.RoomScreen
import com.example.materialhass.screens.SettingsScreen

val destinations = listOf(
    MainScreenDest("Комнаты", Icons.Rounded.Room) {
        RoomScreen(it)
    },
    MainScreenDest("Устройства", Icons.Rounded.Devices) {
        DevicesScreen(it)
    },
    MainScreenDest("Настройки", Icons.Rounded.Settings) {
        SettingsScreen(it)
    },
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenNavigation(navControllerGeneral: NavHostController) { //
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            destinations.forEach { dest ->
                NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == dest.name } == true,
                    onClick = {
                        navController.navigate(dest.name) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            dest.icon, contentDescription = null
                        )
                    },
                    label = { Text(text = dest.name) })
            }
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = destinations.first().name,
                modifier = Modifier.padding(3.dp)
            ) {
                for (dest in destinations) {
                    composable(dest.name) {
                        dest.content(navControllerGeneral)
                    }
                }
            }
        }
    }
}