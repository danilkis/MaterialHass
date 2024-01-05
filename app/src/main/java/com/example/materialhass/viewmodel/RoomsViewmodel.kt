package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceHub
import androidx.lifecycle.ViewModel
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.Services.APIService
import com.example.materialhass.model.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val api = APIService.getAPI(); // Replace YourAPI with your actual API interface

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
                // You can create Room objects based on the room names received from the API
                // You might need to fetch images and icons for each room based on their names or IDs
                // For now, this example uses placeholders for images and icons


                // This is a placeholder URL, replace it with actual URLs if availabl
                val russianName = translitToRussian[roomName] ?: roomName
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