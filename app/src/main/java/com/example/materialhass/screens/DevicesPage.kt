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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.materialhass.customcomponents.ClimateCard
import com.example.materialhass.customcomponents.CoverCard
import com.example.materialhass.customcomponents.LightCard
import com.example.materialhass.customcomponents.SwitchCard
import com.example.materialhass.customcomponents.TypeDivider
import com.example.materialhass.viewmodel.DevicesViewmodel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: DevicesViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val devicesList by viewModel.Devices.collectAsState(initial = mutableListOf())
    val groupedDevices = devicesList.groupBy { it.type }
    val coroutineScope = rememberCoroutineScope()

    val typeIconMap = mapOf(
        "light" to Icons.Default.LightbulbCircle,
        "cover" to Icons.Default.RollerShades,
        "climate" to Icons.Default.HeatPump,
        "switch" to Icons.Default.RadioButtonChecked
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
