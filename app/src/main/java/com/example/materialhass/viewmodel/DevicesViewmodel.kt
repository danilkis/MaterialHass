package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RollerShades
import androidx.compose.material.icons.filled.Straight
import androidx.compose.material.icons.filled.VerticalShades
import androidx.compose.material.icons.filled.Waves
import androidx.lifecycle.ViewModel
import com.example.materialhass.models.Devices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DevicesViewmodel : ViewModel() {
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
            deviceList.add(Devices(2, "light.bulb1", "Лампа", "light", Icons.Default.Lightbulb))
            deviceList.add(Devices(3, "light.bulb2", "Люстра", "light", Icons.Default.Circle))
            deviceList.add(Devices(4, "light.bulb3", "Торшер", "light",Icons.Default.LightMode ))
            deviceList.add(Devices(6, "cover.shades", "Жалюзи", "cover", Icons.Default.RollerShades))
            deviceList.add(Devices(7, "cover.curtains", "Шторы", "cover", Icons.Default.VerticalShades))
            deviceList.add(Devices(8, "cover.gates", "Ворота", "cover", Icons.Default.Garage))
            deviceList.add(Devices(9, "climate.floor", "Теплый пол", "climate", Icons.Default.HeatPump))
            deviceList.add(Devices(10, "climate.radiator", "Радиатор", "climate", Icons.Default.Waves))
            deviceList.add(Devices(11, "climate.conditioner", "Кондиционер", "climate", Icons.Default.AcUnit))
            return@withContext deviceList
        }
    }

    suspend fun reloadDevices() {
        _devices.emit(getDevices())
    }

}