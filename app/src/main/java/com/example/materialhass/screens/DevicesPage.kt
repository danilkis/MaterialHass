package com.example.materialhass.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.RadioButtonChecked
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
import com.example.materialhass.customcomponents.SwitchCard
import com.example.materialhass.customcomponents.TypeDivider
import com.example.materialhass.model.Device
import com.example.materialhass.viewmodel.DevicesViewmodel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: DevicesViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val devicesList by viewModel.device.collectAsState(initial = mutableListOf())

    val typeIconMap = mapOf(
        "light" to Icons.Default.LightbulbCircle,
        "cover" to Icons.Default.RollerShades,
        "climate" to Icons.Default.HeatPump,
        "switch" to Icons.Default.RadioButtonChecked
    )

    val devicesMap = mapOf(
        "light" to getDevicesByType("light", devicesList),
        "cover" to getDevicesByType("cover", devicesList),
        "climate" to getDevicesByType("climate", devicesList),
        "switch" to getDevicesByType("switch", devicesList),
    )

    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 4.dp, 0.dp, 75.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 3
    ) {
        val modifier = Modifier
            .padding(4.dp)
            .weight(0.5f)
        devicesMap.forEach { type ->
            typeIconMap[type.key]?.let { TypeDivider(type = getTypeName(type.key), icon = it) }
            when(type.key) {
                "light" -> type.value.forEach { device ->  LightCard(device, modifier, viewModel)}
                "cover" -> type.value.forEach { device ->  CoverCard(device, modifier, viewModel) }
                "climate" -> type.value.forEach { device ->  ClimateCard(device, modifier, viewModel) }
                "switch" -> type.value.forEach { device ->  SwitchCard(device, modifier, viewModel) }
            }
        }
    }
}

fun getDevicesByType(type: String, devices: List<Device>): List<Device> {
    return devices.filter {it.type == type }
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
