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
    val roomsViewmodel = RoomsViewmodel()
    val ctx = LocalContext.current
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
        ) {
            LaunchedEffect(true) {
                roomsViewmodel.initializeContext(ctx)
            }
            val roomId: Int = it.arguments?.getInt("roomId") ?: 0
            val rooms by roomsViewmodel.Rooms.collectAsState(initial = listOf())
            rooms.forEach { it ->
                if (it.id == roomId) {
                    RoomDevicesScreen(it, navController)
                }
            }
        }
    }
}