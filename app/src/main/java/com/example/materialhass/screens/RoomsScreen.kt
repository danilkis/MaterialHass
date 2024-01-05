package com.example.materialhass.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.materialhass.customcomponents.RoomCard
import com.example.materialhass.customcomponents.RoomModifyDialog
import com.example.materialhass.models.Room
import com.example.materialhass.viewmodel.RoomsViewmodel
import io.ktor.websocket.Frame

@Composable
fun RoomScreen(navController: NavController, viewmodel: RoomsViewmodel = viewModel()) {
    val rooms by viewmodel.Rooms.collectAsState(initial = mutableListOf())
    if (!rooms.isEmpty()) {
        RoomsList(rooms = rooms, navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsList(rooms: MutableList<Room>, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val openDialog = { showDialog = true }

    if (showDialog) {
        RoomModifyDialog(showDialog, {showDialog = false})
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(rooms) { room ->
            RoomCard(
                room = room,
                room.id.toString(),
                navController = navController,
                openDialog
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
