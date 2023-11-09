package com.example.materialhass.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bento
import androidx.lifecycle.ViewModel
import com.example.materialhass.models.Room
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
        return withContext(Dispatchers.Main) {
            val roomList: MutableList<Room> = mutableListOf()
            roomList.add(
                Room(
                    0,
                    "Кухня",
                    "https://proreiling.ru/wp-content/uploads/5/9/d/59d46a21ac5629beea300f92ad418b98.png",
                    Icons.Default.Bento
                )
            )
            roomList.add(
                Room(
                    1,
                    "Спальня",
                    "https://mydizajn.ru/wp-content/uploads/2016/11/Modern-Bedroom-3-1.jpg",
                    Icons.Default.Bed
                )
            ) //TODO: Затычка
            return@withContext roomList
        }
    }

    suspend fun reloadRooms() {
        _rooms.emit(getRooms())
    }

}