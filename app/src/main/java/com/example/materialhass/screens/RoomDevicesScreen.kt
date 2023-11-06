package com.example.materialhass.screens

import android.bluetooth.BluetoothClass.Device
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.materialhass.customcomponents.RoomCard
import com.example.materialhass.models.Devices
import com.example.materialhass.models.Room
import com.example.materialhass.viewmodel.RoomDevicesViewmodel
import com.example.materialhass.viewmodel.RoomsViewmodel

@Composable
fun RoomDeviceScreen(navController: NavController, viewmodel: RoomDevicesViewmodel = viewModel())
{
    val devices by viewmodel.Devices.collectAsState(initial = mutableListOf() )
    if (!devices.isEmpty())
    {
        //RoomsList(rooms = rooms, navController)
    }
}
@Composable
fun DevicesList(rooms: MutableList<Devices>, navController: NavController)
{
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp))
    {
        items(rooms){ room ->
            //RoomCard(room, room.id.toString(), navController = navController)
            //Spacer(modifier = Modifier.height(14.dp))
        }
    }
}