package com.example.materialhass.viewmodel

import android.util.Log
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.models.Devices
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomDevicesViewmodel : DevicesViewmodel() {
    private val _filterDevices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var filteredDevices: StateFlow<MutableList<Devices>> = _filterDevices

    var roomName: String = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadFilteredDevices()
        }
    }

    suspend fun roomDevices(): MutableList<Devices> {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
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
        _filterDevices.emit(roomDevices())
    }
}