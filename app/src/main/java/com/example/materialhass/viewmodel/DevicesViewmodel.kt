package com.example.materialhass.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.materialhass.API.Entity
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.PositionBody
import com.example.materialhass.API.TempBody
import com.example.materialhass.API.ToggleBody
import com.example.materialhass.API.TurnOnWithBrightnessBody
import com.example.materialhass.Services.APIService
import com.example.materialhass.model.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class DevicesViewmodel : ViewModel() {
    private val _device = MutableStateFlow<MutableList<Device>>(mutableListOf())

    var device: StateFlow<MutableList<Device>> = _device

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadDevices()
        }
    }

    suspend fun getDevices(): MutableList<Device> {

        return withContext(Dispatchers.IO) {
            // Create the API instance

            val api = APIService.getAPI();

            // Make the request
            val entities = api.getStates()

            // Filter the entities
            val filteredEntities = entities.filter{  entityFilter(it) }

            // Convert the filtered entities into a list of Devices
            val deviceList = filteredEntities.mapIndexed { index, entity ->
                val type = entity.entity_id.split(".")[0]
                val useExtendedControls = controlsFilter(entity)

                Device(
                    id = index,
                    name = entity.entity_id,
                    friendly_name = entity.attributes["friendly_name"] as String,
                    state = entity.state,
                    type = type,
                    icon = entity.attributes["icon"] as String? ?: "circle",
                    extended_controls = useExtendedControls,
                    brightness = entity.attributes["brightness"] as Double?,
                    position = entity.attributes["current_position"] as Double?,
                    temperature = entity.attributes["temperature"] as Double?
                )
            }

            return@withContext deviceList.toMutableList()
        }
    }
    private fun entityFilter(entity: Entity): Boolean {
        return entity.entity_id.startsWith("switch.") ||
                entity.entity_id.startsWith("cover.") ||
                entity.entity_id.startsWith("climate.") ||
                entity.entity_id.startsWith("light.")
    }

    private fun controlsFilter(entity: Entity): Boolean {
        return  entity.attributes["current_position"] != null ||
                entity.attributes["light.brightness"] != null ||
                entity.attributes["brightness"] != null
    }


    suspend fun reloadDevices() {
        _device.emit(getDevices())
    }

    suspend fun toggleLight(device: Device) {
        return withContext(Dispatchers.IO) {
            performToggleAction(device, action = { body, api -> api.toggleLight(body)})
        }
    }

    suspend fun toggleSwitch(device: Device) {
        return withContext(Dispatchers.IO) {
            performToggleAction(device, action = { body, api -> api.toggleSwitch(body)})
        }
    }

    suspend fun setBrightness(device: Device, brightness: Int) {
        return withContext(Dispatchers.IO) {
            performBrightnessAction(device, brightness, action = { body, api -> api.lightBrightness(body)} )
        }
    }

    suspend fun openCover(device: Device) {
        return withContext(Dispatchers.IO) {
            performToggleAction(device, action = { body, api -> api.coverOpen(body)})
        }
    }

    suspend fun closeCover(device: Device) {
        return withContext(Dispatchers.IO) {
            performToggleAction(device, action = { body, api -> api.coverClose(body)})
        }
    }

    suspend fun stopCover(device: Device) {
        return withContext(Dispatchers.IO) {
            performToggleAction(device, action = { body, api -> api.coverStop(body)})
        }
    }
    suspend fun setPosition(device: Device, pos: Int) {
        return withContext(Dispatchers.IO) {
            val api = APIService.getAPI();
            val body = PositionBody(entity_id = device.name, position = pos)
            Log.e("API", body.toString() + " | " + api.toString())
            api.setCoverPosition(body)
            reloadDevices()
        }
    }
    suspend fun performToggleAction(device: Device, action: suspend (ToggleBody, HomeAssistantAPI) -> Unit ) {
        val api = APIService.getAPI();
        val body = ToggleBody(entity_id = device.name) // replace "light.Ceiling" with your actual entity_id
        action(body, api)
        reloadDevices()
    }

    suspend fun performBrightnessAction(device: Device, brightness: Int, action: suspend (TurnOnWithBrightnessBody, HomeAssistantAPI) -> Unit ) {
        val api = APIService.getAPI();
        val body = TurnOnWithBrightnessBody(entity_id = device.name, brightness = brightness)// replace "light.Ceiling" with your actual entity_id
        action(body, api)
        reloadDevices()
    }
    suspend fun toggleClimate(device: Device) {
        try
        {
            return withContext(Dispatchers.IO) {
                val api = APIService.getAPI();
                val body = ToggleBody(entity_id = device.name)
                Log.e("API", body.toString() + "  |  " + device.state)

                if(device.state != "off")
                {
                    api.offHvac(body)
                    reloadDevices()
                    return@withContext
                }

                api.onHvac(body)
                reloadDevices()
            }
        }
        catch (e: Exception)
        {
            Log.e("API", "Climate error")
        }
    }
    suspend fun setTemperature(device: Device, temp: Double) {
        try
        {
            return withContext(Dispatchers.IO) {
                val api = APIService.getAPI();
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
