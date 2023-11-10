package com.example.materialhass.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.RollerShades
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.materialhass.customcomponents.ClimateCard
import com.example.materialhass.customcomponents.CoverCard
import com.example.materialhass.customcomponents.LightCard
import com.example.materialhass.customcomponents.TypeDivider
import com.example.materialhass.viewmodel.DevicesViewmodel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: DevicesViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val devices_list by viewModel.Devices.collectAsState(initial = mutableListOf())
    val groupedDevices = devices_list.groupBy { it.type }
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 2
    ) {
        groupedDevices.forEach { (type, device) ->
            // This is your divider
            when (type) {
                "light" -> TypeDivider(type = "Свет", icon = Icons.Default.LightbulbCircle)
                "cover" -> TypeDivider(type = "Жалюзи", icon = Icons.Default.RollerShades)
                "climate" -> TypeDivider(type = "Климат", icon = Icons.Default.HeatPump)
            }
            device.forEach { item ->
                when (item.type) {
                    "light" -> Box(Modifier.weight(0.5f)) {
                        LightCard(
                            item,
                            Modifier.padding(4.dp)
                        )
                    } //
                    "cover" -> CoverCard(item,
                        Modifier
                            .weight(0.5f)
                            .padding(4.dp))
                    "climate" -> ClimateCard(item, Modifier.padding(4.dp))
                }
            }
        }
    }
}