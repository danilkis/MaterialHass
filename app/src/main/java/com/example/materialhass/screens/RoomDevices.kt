package com.example.materialhass.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.RollerShades
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.materialhass.customcomponents.ClimateCard
import com.example.materialhass.customcomponents.LightCard
import com.example.materialhass.customcomponents.RoomCircle
import com.example.materialhass.models.Devices
import com.example.materialhass.models.Room
import com.example.materialhass.viewmodel.RoomDevicesViewmodel

@Composable
fun RoomHeader(room: Room)
{
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically)
        {
            RoomCircle(id = "0", icon = room.icon, size = 55.dp)
            Spacer(Modifier.width(10.dp))
            Column() {
                Text(room.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(4.dp))
                Text(room.id.toString(), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
@Composable
fun TypeDivider(type: String, icon: ImageVector)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically)
    {
        Text(type, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.width(10.dp))
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}

@Composable
fun RoomDevicesScreen(room: Room, navController: NavController, viewModel: RoomDevicesViewmodel = androidx.lifecycle.viewmodel.compose.viewModel())
{
    val devices_list by viewModel.Devices.collectAsState(initial =  mutableListOf() )
    Column(modifier = Modifier.fillMaxSize())
    {
        RoomHeader(room)
        Divider()
        TypeDivider(type = "Свет", icon = Icons.Default.LightbulbCircle)
        DevicePage(devices_list, "light")
        TypeDivider(type = "Климат", icon = Icons.Default.HeatPump)
        DevicePage(devices_list, "climate")
        TypeDivider(type = "Жалюзи", icon = Icons.Default.RollerShades)
        DevicePage(devices_list, "cover")
    }
}

@Composable
fun DevicePage(devices: MutableList<Devices>, type_filter: String)
{
    val result_list = devices.filter { it.type == type_filter }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(result_list) { device ->
            if(type_filter == "light")
            {
                LightCard(device)
            }
            if (type_filter == "climate")
            {
                ClimateCard(device)
            }
        }
    }
}