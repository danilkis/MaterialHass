package com.example.supabasedemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.materialhass.SharedPreference

@Composable
fun Auth(navController: NavController) {
    val ctx = LocalContext.current
    val sharedPreference = SharedPreference(ctx)
    if (!sharedPreference.GetBool("Init_setup")) {
        var serverURL by remember { mutableStateOf("") }
        var ApiKey by remember { mutableStateOf("") }
        var Name by remember { mutableStateOf("") }
        var UserReady by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Добро пожаловать в MaterialHass!",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = Name,
                onValueChange = { Name = it },
                placeholder = { Text(text = "Ваше имя") }
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = serverURL,
                onValueChange = { serverURL = it },
                placeholder = { Text(text = "URL сервера") }
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = ApiKey,
                onValueChange = { ApiKey = it },
                placeholder = { Text(text = "Ключ API") }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {
                sharedPreference.SaveBool("Init_setup", true)
                sharedPreference.SaveString("Name", Name)
                sharedPreference.SaveString("ServerUrl", serverURL)
                sharedPreference.SaveString("APIkey", ApiKey)
                UserReady = true
            }) {
                Text(text = "Done")
            }
            if (UserReady) {
                LaunchedEffect(true)
                {
                    navController.navigate("helloScreen")
                }
            }
        }
    } else {
        LaunchedEffect(true)
        {
            navController.navigate("helloScreen")
        }
    }
}