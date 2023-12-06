package com.example.materialhass.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Light
import androidx.lifecycle.ViewModel
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.PositionBody
import com.example.materialhass.API.TempBody
import com.example.materialhass.API.ToggleBody
import com.example.materialhass.API.TurnOnWithBrightnessBody
import com.example.materialhass.models.Devices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class DevicesViewmodel : ViewModel() {
    private val _devices = MutableStateFlow<MutableList<Devices>>(mutableListOf())

    var Devices: StateFlow<MutableList<Devices>> = _devices

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadDevices()
        }
    }

    suspend fun getDevices(): MutableList<Devices> {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            // Create the API instance
            val api = retrofit.create(HomeAssistantAPI::class.java)

            // Make the request
            val entities = api.getStates()

            // Filter the entities
            val filteredEntities = entities.filter {it.entity_id.startsWith("switch.") || it.entity_id.startsWith("cover.") || it.entity_id.startsWith("climate.") || it.entity_id.startsWith("light.") }
            // Convert the filtered entities into a list of Devices
            val deviceList = filteredEntities.mapIndexed { index, entity ->
                val type = entity.entity_id.split(".")[0]
                val icon = when (type) {
                    "cover" -> Icons.Default.Blinds
                    "climate" -> Icons.Default.AcUnit
                    "light" -> Icons.Default.Light
                    else -> Icons.Default.DeviceHub
                }
                val extended_controls = entity.attributes["current_position"] != null || entity.attributes["light.brightness"] != null || entity.attributes["brightness"] != null

                Devices(
                    id = index,
                    name = entity.entity_id,
                    friendly_name = entity.attributes["friendly_name"] as String,
                    state = entity.state,
                    type = type,
                    icon = icon,
                    extended_controls = extended_controls,
                    brightness = entity.attributes["brightness"] as Double?,
                    position = entity.attributes["current_position"] as Double?,
                    temperature = entity.attributes["temperature"] as Double?
                )
            }

            return@withContext deviceList.toMutableList()
        }
    }


    suspend fun reloadDevices() {
        _devices.emit(getDevices())
    }

    suspend fun toggleLight(device: Devices) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            // Create the API instance
            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = ToggleBody(entity_id = device.name) // replace "light.Ceiling" with your actual entity_id
            api.toggleLight(body)
            reloadDevices()
        }
    }

    suspend fun toggleSwitch(device: Devices) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            // Create the API instance
            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = ToggleBody(entity_id = device.name) // replace "light.Ceiling" with your actual entity_id
            api.toggleSwitch(body)
            reloadDevices()
        }
    }

    suspend fun setBrightness(device: Devices, brightness: Int) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = TurnOnWithBrightnessBody(entity_id = device.name, brightness = brightness)
            api.lightBrightness(body)
            reloadDevices()
        }
    }

    suspend fun openCover(device: Devices) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = ToggleBody(entity_id = device.name)
            api.coverOpen(body)
            reloadDevices()
        }
    }

    suspend fun closeCover(device: Devices) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = ToggleBody(entity_id = device.name)
            api.coverClose(body)
            reloadDevices()
        }
    }

    suspend fun stopCover(device: Devices) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = ToggleBody(entity_id = device.name)
            api.coverStop(body)
            reloadDevices()
        }
    }
    suspend fun setPosition(device: Devices, pos: Int) {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(HomeAssistantAPI::class.java)
            val body = PositionBody(entity_id = device.name, position = pos)
            Log.e("API", body.toString() + " | " + api.toString())
            api.setCoverPosition(body)
            reloadDevices()
        }
    }

    suspend fun toggleClimate(device: Devices) {
        try
        {
            return withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pavlovskhome.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create(HomeAssistantAPI::class.java)
                val body = ToggleBody(entity_id = device.name)
                Log.e("API", body.toString() + "  |  " + device.state)
                if(device.state != "off")
                {
                    api.offHvac(body)
                }
                else
                {
                    api.onHvac(body)
                }
                reloadDevices()
            }
        }
        catch (e: Exception)
        {
            Log.e("API", "Climate error")
        }
    }
    suspend fun setTemperature(device: Devices, temp: Double) {
        try
        {
            return withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pavlovskhome.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create(HomeAssistantAPI::class.java)
                val body = TempBody(entity_id = device.name, temp)
                Log.e("API", body.toString() + "  |  " + device.state)
                api.setTemperature(body)
                reloadDevices()
            }
        }
        catch (e: Exception)
        {
            Log.e("API", "Climate error")
        }
    }
}
