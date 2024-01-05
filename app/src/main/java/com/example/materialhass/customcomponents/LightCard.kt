package com.example.materialhass.customcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.materialhass.model.Device
import com.example.materialhass.viewmodel.DevicesViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LightCard(device: Device, modifier: Modifier, viewmodel: DevicesViewmodel) {
    val corutineScope = rememberCoroutineScope()
    OutlinedCard(modifier) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DeviceCircle(id = "0", icon = device.icon)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        device.friendly_name,
                        style = MaterialTheme.typography.bodyLarge,
                        softWrap = false,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        device.state,
                        style = MaterialTheme.typography.bodyMedium,
                        softWrap = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                OutlinedIconButton(onClick =
                {
                    corutineScope.launch {
                        viewmodel.toggleLight(device)
                    }
                }, enabled = device.state != "unavailable") {
                    Icon(
                        Icons.Default.PowerSettingsNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (device.extended_controls) {
                Column {
                    var sliderPosition by remember { mutableStateOf(0f) }
                    sliderPosition = device.brightness?.toFloat()?.div(25.5f) ?: 0f
                    Slider(
                        value = sliderPosition,
                        valueRange = 0f..10f,
                        onValueChange = {
                            val brightnessValue = (it * 25.5f).toInt() // Convert slider position to brightness value
                            sliderPosition = it
                            corutineScope.launch {
                                viewmodel.setBrightness(device, brightnessValue)
                            } // Invoke the function on brightness change
                        },
                    )
                }
            }
        }
    }
}