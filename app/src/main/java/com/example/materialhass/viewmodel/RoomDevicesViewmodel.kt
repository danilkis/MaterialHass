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
import com.example.materialhass.models.Devices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomDevicesViewmodel : ViewModel() {
    private val _devices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var Devices: StateFlow<MutableList<Devices>> = _devices

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadDevices()
        }
    }

    suspend fun getDevices(): MutableList<Devices> {
        val deviceList: MutableList<Devices> = mutableListOf()
        return withContext(Dispatchers.Main) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Create the API instance
            val api = retrofit.create(HomeAssistantAPI::class.java)

            // Make the request
            val entities = api.getStates()

            // Filter the entities
            val filteredEntities = entities.filter { it.entity_id.startsWith("cover.") || it.entity_id.startsWith("climate.") || it.entity_id.startsWith("light.") }
            // Convert the filtered entities into a list of Devices
            val deviceList = filteredEntities.mapIndexed { index, entity ->
                val type = entity.entity_id.split(".")[0]
                val icon = when (type) {
                    "cover" -> Icons.Default.Blinds
                    "climate" -> Icons.Default.AcUnit
                    "light" -> Icons.Default.Light
                    else -> Icons.Default.DeviceHub
                }
                val extended_controls = entity.attributes["current_position"] != null || entity.attributes["light.brightness"] != null

                Devices(
                    id = index,
                    name = entity.entity_id,
                    friendly_name = entity.attributes["friendly_name"] as String,
                    state = entity.state,
                    type = type,
                    icon = icon,
                    extended_controls = extended_controls
                )
            }

            return@withContext deviceList.toMutableList()
        }
    }

    suspend fun reloadDevices() {
        _devices.emit(getDevices())
    }

}