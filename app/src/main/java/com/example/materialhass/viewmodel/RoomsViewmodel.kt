package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bento
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Room
import androidx.lifecycle.ViewModel
import com.example.materialhass.API.HomeAssistantAPI
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.models.Devices
import com.example.materialhass.models.Room
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomsViewmodel : ViewModel() {
    private val _rooms = MutableStateFlow<MutableList<Room>>(mutableListOf())

    var Rooms: StateFlow<MutableList<Room>> = _rooms

    init {
        CoroutineScope(Dispatchers.IO).launch {
            reloadRooms()
        }
    }

    suspend fun getRooms(): MutableList<Room> {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pavlovskhome.ru/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            // Create the API instance
            val api = retrofit.create(HomeAssistantAPI::class.java) // Replace YourAPI with your actual API interface

            // Make the request using the appropriate API call
            val response = api.getRooms(TemplateBody("{{ areas() }}"))

            // Process the response and convert it into a list of Room objects
            val roomList = mutableListOf<Room>()
            val translitToRussian = mapOf(
                "gostinaia" to "Гостиная",
                "kukhnia" to "Кухня",
                "spalnia" to "Спальня",
                "dvor" to "Двор"
                // Добавьте другие комнаты по аналогии, если нужно
            )
            response.forEachIndexed { index, roomName ->
                roomList.add(
                    Room(
                        id = index,
                        name = roomName,
                        picture_Url = "https://vita-property.ru/wp-content/uploads/a/c/6/ac6cbd136254d7841f86714d1acbba5c.jpeg",
                        icon = Icons.Default.DeviceHub
                    )
                )
            }

            return@withContext roomList
        }
    }
    suspend fun reloadRooms() {
        _rooms.emit(getRooms())
    }
}