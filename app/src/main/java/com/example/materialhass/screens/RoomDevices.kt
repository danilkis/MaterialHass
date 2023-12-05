package com.example.materialhass.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.RollerShades
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.materialhass.customcomponents.ClimateCard
import com.example.materialhass.customcomponents.LightCard
import com.example.materialhass.customcomponents.RoomCircle
import com.example.materialhass.customcomponents.TypeDivider
import com.example.materialhass.models.Devices
import com.example.materialhass.models.Room
import com.example.materialhass.viewmodel.RoomDevicesViewmodel
import kotlinx.coroutines.launch

@Composable
fun RoomHeader(room: Room) {
    Column(modifier = Modifier.height(250.dp))
    {
        Image(
            painter = rememberAsyncImagePainter(room.picture_Url),
            contentDescription = null,
            modifier = Modifier
                .height(170.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            RoomCircle(icon = room.icon, size = 55.dp)
            Spacer(Modifier.width(10.dp))
            Column() {
                Text(room.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(4.dp))
                Text(room.id.toString(), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomDevicesScreen(
    room: Room,
    navController: NavController,
    viewModel: RoomDevicesViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val devices_list by viewModel.Devices.collectAsState(initial = mutableListOf())
    viewModel.roomName = room.name
    LaunchedEffect(Unit) { viewModel.roomDevices() }
    Column(modifier = Modifier.fillMaxSize())
    {
        RoomHeader(room)
        Divider()
        DevicePage(viewModel, devices_list)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DevicePage(viewmodel: RoomDevicesViewmodel, devices: List<Devices>) {
    val corutineScope = rememberCoroutineScope()
    val groupedDevices = devices.groupBy { it.type }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 2
    ) {
        groupedDevices.forEach { (type, device) ->
            // This is your divider
            if (type == "light") {
                TypeDivider(type = "Свет", icon = Icons.Default.LightbulbCircle)
            }
            if (type == "cover") {
                TypeDivider(type = "Жалюзи", icon = Icons.Default.RollerShades)
            }
            if (type == "climate") {
                TypeDivider(type = "Климат", icon = Icons.Default.HeatPump)
            }
            device.forEach { item ->
                if (item.type == "light") {
                    LightCard(item, Modifier.weight(0.25f), viewmodel = viewmodel)

                }
                if (item.type == "cover") {
                    //CoverCard(item, Modifier.weight(0.5f), viewmodel)
                    //if(rowDevices.size > 1) { CoverCard(rowDevices[1]) }
                }
                if (item.type == "climate") {
                    ClimateCard(item,
                        Modifier
                            .weight(0.5f)
                            .padding(4.dp), viewmodel)
                }
            }
        }
    }
}