package com.example.materialhass.viewmodel

import android.util.Log
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.models.Devices
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomDevicesViewmodel : DevicesViewmodel() {
    private val _filterDevices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var filteredDevices: StateFlow<MutableList<Devices>> = _filterDevices

    var roomName: String = ""
    suspend fun roomDevices(): MutableList<Devices> {
        val gson = GsonBuilder().setLenient().create()
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            // Make the request using the appropriate API call with the roomName parameter
            val response = api.getRoomDevices(TemplateBody("{{ area_entities('$roomName') }}"))
            Log.e("API ROOMS", response.toString())
            val roomDevices = Devices.collect {
                it.filter { response.contains(it.name) }
            }
            Log.e("API ROOMS", roomDevices)
            return@withContext roomDevices
        }
    }

    suspend fun reloadFilteredDevices() {
        _filterDevices.emit(roomDevices())
    }
}