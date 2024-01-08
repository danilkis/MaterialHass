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
    val viewModel: RoomsViewmodel = viewModel() // Retrieve the ViewModel instance

    val rooms by viewModel.Rooms.collectAsState(initial = listOf())
    val navController = rememberNavController()
    val ctx = LocalContext.current
    LaunchedEffect(true) {
        viewModel.initializeContext(ctx)
    }
    //val personVm = PersonsViewmodel()
    //val persons by personVm.newPersons.collectAsState(initial = listOf())
    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            Auth(navController)
            //Ввод адреса сервера
        }
        composable("helloScreen") {
            Hello(navController)
            //Авторизация
        }
        composable("mainScreen") {
            MainScreen(navController)
            //Переход на главный экран
        }
        composable(
            "room/{roomId}",
            arguments = listOf(navArgument("roomId") { type = NavType.IntType })
        ) { backStackEntry ->
            val roomId: Int = backStackEntry.arguments?.getInt("roomId") ?: 0
            val selectedRoom = rooms.find { it.id == roomId }

            if (selectedRoom != null) {
                RoomDevicesScreen(selectedRoom, navController)
            } else {
                Log.e("NAVS", "Наебни говна олух")
            }
        }

    }
}