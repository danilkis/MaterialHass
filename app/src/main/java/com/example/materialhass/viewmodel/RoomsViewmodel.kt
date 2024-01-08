package com.example.materialhass.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialhass.API.TemplateBody
import com.example.materialhass.Services.APIService
import com.example.materialhass.model.Room
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomsViewmodel() : ViewModel() {
    private val _rooms = MutableStateFlow<MutableList<Room>>(mutableListOf())
    var Rooms: StateFlow<MutableList<Room>> = _rooms
    companion object {
        lateinit var ctx: Context // This should be initialized before usage
    }
    init {
        CoroutineScope(Dispatchers.IO).launch {
            //reloadRooms()
        }
    }

    suspend fun getRooms(ctx: Context): MutableList<Room> {
        return withContext(Dispatchers.IO) {
            val api = APIService.getAPI();
            val sharedPreferences = ctx.getSharedPreferences("RoomData", Context.MODE_PRIVATE)
            val gson = Gson()

            // Make the request using the appropriate API call
            val response = api.getRooms(TemplateBody("{{ areas() }}"))

            // Process the response and convert it into a list of Room objects
            val roomList = mutableListOf<Room>()
            response.forEachIndexed { index, roomName ->
                // Check if the room name exists in shared preferences
                val json = sharedPreferences.getString(roomName, null)
                if (json != null) {
                    // If it exists, use the room from shared preferences
                    val room = gson.fromJson(json, Room::class.java)
                    Log.e("ROOM", room.toString())
                    roomList.add(room)
                } else {
                    // If it doesn't exist, use the room from the API
                    roomList.add(
                        Room(
                            id = index,
                            name = roomName,
                            picture_Url = "",
                            displayName = "",
                            icon = "mdi:fridge"
                        )
                    )
                }
            }

            return@withContext roomList
        }
    }

    suspend fun reloadRooms(ctx: Context) {
        _rooms.emit(getRooms(ctx))
    }

    fun initializeContext(context: Context) {
        ctx = context
        viewModelScope.launch {
            reloadRooms(context)
        }
    }
    suspend fun saveRoom(room: Room) {
        withContext(Dispatchers.IO) {
            val sharedPreferences = ctx.getSharedPreferences("RoomData", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(room)
            editor.putString(room.name, json)
            editor.apply()

            // After saving, reload the rooms and emit the updated list
            Log.e("EMIT", "Room updated")
            _rooms.emit(getRooms(ctx))
        }
    }
}