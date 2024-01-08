package com.example.materialhass.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.RadioButtonChecked
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
import com.example.materialhass.customcomponents.CoverCard
import com.example.materialhass.customcomponents.DeviceCircle
import com.example.materialhass.customcomponents.LightCard
import com.example.materialhass.customcomponents.SwitchCard
import com.example.materialhass.customcomponents.TypeDivider
import com.example.materialhass.model.Device
import com.example.materialhass.model.Room
import com.example.materialhass.viewmodel.DevicesViewmodel
import com.example.materialhass.viewmodel.RoomDevicesViewmodel

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
            DeviceCircle(id = "",icon = room.icon, size = 55.dp)
            Spacer(Modifier.width(10.dp))
            Column {
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
    viewModel.roomName = room.name
    val devices_list by viewModel.filteredDevice.collectAsState(initial = mutableListOf())
    Log.e("Devices", devices_list.toString())
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
fun DevicePage(viewModel: RoomDevicesViewmodel, devices: List<Device>) {
    val typeIconMap = mapOf(
        "light" to Icons.Default.LightbulbCircle,
        "cover" to Icons.Default.RollerShades,
        "climate" to Icons.Default.HeatPump,
        "switch" to Icons.Default.RadioButtonChecked
    )
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
        groupedDevices.forEach { (type, devices) ->
            typeIconMap[type]?.let { TypeDivider(type = getTypeName(type), icon = it) }

            devices.forEach { item ->
                when (item.type) {
                    "light" -> LightCard(
                        item,
                        Modifier
                            .padding(4.dp)
                            .weight(0.5f),
                        viewModel
                    )
                    "cover" -> CoverCard(
                        item,
                        Modifier
                            .padding(4.dp)
                            .weight(0.5f),
                        viewModel
                    )
                    "climate" -> ClimateCard(item, Modifier.padding(4.dp), viewModel)
                    "switch" -> SwitchCard(item, Modifier.padding(4.dp), viewModel)
                }
            }
        }
    }
}

@Composable
private fun getTypeName(type: String): String {
    return when (type) {
        "light" -> "Свет"
        "cover" -> "Жалюзи"
        "climate" -> "Климат"
        "switch" -> "Переключатель"
        else -> ""
    }
}
