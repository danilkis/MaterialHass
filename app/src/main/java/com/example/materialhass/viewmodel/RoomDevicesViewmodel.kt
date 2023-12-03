package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Straight
import androidx.lifecycle.ViewModel
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.ToggleBody
import com.example.materialhass.models.Devices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomDevicesViewmodel : DevicesViewmodel() {
    private val _devices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

}