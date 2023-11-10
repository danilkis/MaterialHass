package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Straight
import androidx.lifecycle.ViewModel
import com.example.materialhass.models.Devices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomDevicesViewmodel : ViewModel() {
    private val _devices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var Devices: StateFlow<MutableList<Devices>> = _devices

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadDevices()
        }
    }

    suspend fun getDevices(): MutableList<Devices> {
        return withContext(Dispatchers.Main) {
            val deviceList: MutableList<Devices> = mutableListOf()
            deviceList.add(Devices(0, "light.lamp", "Лампа", "light", Icons.Default.Light)) //TODO: Затычка
            deviceList.add(Devices(1, "light.leds", "Лента", "light", Icons.Default.Straight))
            deviceList.add(Devices(2, "cover.shades", "Жалюзи", "cover", Icons.Default.Blinds))
            deviceList.add(Devices(3, "climate.floor", "Теплый пол", "climate", Icons.Default.HeatPump))
            return@withContext deviceList
        }
    }

    suspend fun reloadDevices() {
        _devices.emit(getDevices())
    }

}