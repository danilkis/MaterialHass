package com.example.materialhass.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.materialhass.customcomponents.ConnectionErrorWarning
import com.example.materialhass.customcomponents.RoomCard
import com.example.materialhass.customcomponents.RoomModifyDialog
import com.example.materialhass.model.Room
import com.example.materialhass.viewmodel.RoomsViewmodel

@Composable
fun RoomScreen(navController: NavController, viewmodel: RoomsViewmodel = viewModel()) {
    val rooms by viewmodel.Rooms.collectAsState(initial = mutableListOf())
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.initializeContext(ctx)
    }
    if (rooms.contains(Room(0,"ERROR", "", "", "")))
    {
        ConnectionErrorWarning()
    }
    else if (rooms.isEmpty()) {
        CircularProgressIndicator()
    }
    else
    {
        RoomsList(rooms = rooms, navController,viewmodel)
    }
}

@Composable
fun RoomsList(rooms: MutableList<Room>, navController: NavController, viewmodel: RoomsViewmodel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    val openDialog = { room: Room ->
        selectedRoom = room
        showDialog = true
    }

    if (showDialog) {
        RoomModifyDialog(
            open = showDialog,
            old_room = selectedRoom,
            onDismiss = { showDialog = false },
            viewmodel
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
    {
        items(rooms) { room ->
            RoomCard(
                room = room,
                navController = navController,
                { openDialog(room) } // Pass the room object to openDialog
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
