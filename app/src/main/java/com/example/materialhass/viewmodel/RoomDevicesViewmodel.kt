package com.example.materialhass.viewmodel

import android.util.Log
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.Services.APIService
import com.example.materialhass.model.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomDevicesViewmodel : DevicesViewmodel() {
    private val _filterDevice = MutableStateFlow<MutableList<Device>>(mutableListOf())

    var filteredDevice: StateFlow<MutableList<Device>> = _filterDevice

    var roomName: String = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadFilteredDevices()
        }
    }

    suspend fun roomDevices(): MutableList<Device> {
        return withContext(Dispatchers.IO) {
            val api = APIService.getAPI();
            // Make the request using the appropriate API call with the roomName parameter
            val response = api.getRoomDevices(TemplateBody("{{ area_entities('$roomName') }}"))
            Log.e("API ROOMS RESPONCE", response.toString())
            val filteredDevices = getDevices().filter { device ->
                response.any { device.name == it }
            }
            Log.e("API ROOMS DEVICXES", filteredDevices.toString())
            return@withContext filteredDevices.toMutableList()
        }
    }

    suspend fun reloadFilteredDevices() {
        _filterDevice.emit(roomDevices())
    }
}