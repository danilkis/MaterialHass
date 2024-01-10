package com.example.materialhass.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.materialhass.SharedPreference
import com.example.materialhass.viewmodel.SettingsViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val ctx = LocalContext.current
    val sharedPreference = SharedPreference(ctx)

    var serverURL by remember { mutableStateOf(sharedPreference.GetString("ServerUrl") ?: "") }
    var ApiKey by remember { mutableStateOf(sharedPreference.GetString("APIkey") ?: "") }
    var Name by remember { mutableStateOf(sharedPreference.GetString("Name") ?: "") }

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

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
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick =
        {
            sharedPreference.SaveString("Name", Name)
            sharedPreference.SaveString("ServerUrl", serverURL)
            sharedPreference.SaveString("APIkey", ApiKey)
        }) {
            Text("Готово")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

