package com.example.materialhass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.materialhass.screens.MainScreen
import com.example.supabasedemo.screens.Auth
import com.example.supabasedemo.screens.Hello
import androidx.navigation.NavHost as NavHost1

@Composable
fun GeneralNavigation() {
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
    }
}