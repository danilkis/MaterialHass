package com.example.supabasedemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.materialhass.SharedPreference
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Auth(navController: NavController) {
    var ctx = LocalContext.current
    val sharedPreference: SharedPreference =SharedPreference(ctx)
    if (!sharedPreference.GetBool("Init_setup"))
    {
        var serverURL by remember { mutableStateOf("") }
        var Username by remember { mutableStateOf("") }
        var Password by remember { mutableStateOf("") }
        var Name by remember { mutableStateOf("") }
        var UserReady by remember {
            mutableStateOf(false)
        }
    Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Welcome to MaterialHass!",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = Name,
            onValueChange = { Name = it },
            placeholder = { Text(text = "Your name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = serverURL,
            onValueChange = { serverURL = it },
            placeholder = { Text(text = "Server URL") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = Username,
            onValueChange = { Username = it },
            placeholder = { Text(text = "Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = Username,
            onValueChange = { Username = it },
            placeholder = { Text(text = "Password") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
                sharedPreference.SaveBool("Init_setup", true)
                sharedPreference.SaveString("Name", Name.toString())
                UserReady = true
            }) {
            Text(text = "Done")
        }
        if (UserReady)
        {
            LaunchedEffect(true)
            {
                navController.navigate("helloScreen")
            }
        }
    }
    }
    else
    {
        LaunchedEffect(true)
        {
            navController.navigate("helloScreen")
        }
    }
}