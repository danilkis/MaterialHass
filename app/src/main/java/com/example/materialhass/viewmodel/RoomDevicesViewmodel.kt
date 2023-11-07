package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bento
import androidx.lifecycle.ViewModel
import com.example.materialhass.models.Devices
import com.example.materialhass.models.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomDevicesViewmodel: ViewModel() {
    private val _devices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var Devices: StateFlow<MutableList<Devices>> = _devices

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadDevices()
        }
    }

    suspend fun getDevices(): MutableList<Devices> {
        return withContext(Dispatchers.Main) {
            var deviceList: MutableList<Devices> = mutableListOf()
            deviceList.add(Devices(0, "light.lamp", "Лампа", "light")) //TODO: Затычка
            deviceList.add(Devices(1, "light.leds", "Лента", "light"))
            deviceList.add(Devices(2, "cover.shades", "Жалюзи", "cover"))
            deviceList.add(Devices(3, "climate.floor", "Теплый пол", "climate"))
            return@withContext deviceList
        }
    }

    suspend fun reloadDevices() {
        _devices.emit(getDevices())
    }

}