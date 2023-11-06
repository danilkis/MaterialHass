package com.example.materialhass.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.materialhass.screens.MainScreen
import com.example.materialhass.screens.RoomDeviceScreen
import com.example.materialhass.screens.RoomDevicesScreen
import com.example.materialhass.viewmodel.RoomsViewmodel
import com.example.supabasedemo.screens.Auth
import com.example.supabasedemo.screens.Hello
import androidx.navigation.NavHost as NavHost1

@Composable
fun GeneralNavigation() {
    val rooms by RoomsViewmodel().Rooms.collectAsState(initial = listOf())
    val navController = rememberNavController()
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
            val roomId: Int = it.arguments?.getInt("roomId") ?: 0
            rooms.forEach { it ->
                if (it.id == roomId) {
                    RoomDevicesScreen(it, navController)
                }
            }
        }
    }
}