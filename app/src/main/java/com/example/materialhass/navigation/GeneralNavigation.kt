package com.example.materialhass.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.materialhass.screens.MainScreen
import com.example.materialhass.screens.RoomDevicesScreen
import com.example.materialhass.viewmodel.RoomsViewmodel
import com.example.supabasedemo.screens.Auth
import com.example.supabasedemo.screens.Hello

@Composable
fun GeneralNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            Auth(navController)
        }
        composable("helloScreen") {
            Hello(navController)
        }
        composable("mainScreen") {
            MainScreen(navController)
        }
        composable(
            "room/{roomId}",
            arguments = listOf(navArgument("roomId") { type = NavType.IntType })
        ) { backStackEntry ->
            val roomId: Int = backStackEntry.arguments?.getInt("roomId") ?: 0

            RoomDevicesScreen(roomId, navController)
        }

    }
}