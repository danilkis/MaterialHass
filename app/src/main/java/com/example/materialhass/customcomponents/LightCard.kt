package com.example.materialhass.customcomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.materialhass.models.Devices

@Composable
fun LightCard(device: Devices)
{
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Row( horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically)
            {
                DeviceCircle(id = "0", icon = Icons.Default.Light)
                Spacer(Modifier.width(10.dp))
                Column() {
                    Text(device.friendly_name, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(4.dp))
                    Text(device.type, style = MaterialTheme.typography.bodyMedium) //TODO: состояние устройства
                }
            }
            Column {
                var sliderPosition by remember { mutableStateOf(0f) }
                Slider(
                    value = sliderPosition,
                    valueRange = 0f..10f,
                    onValueChange = { sliderPosition = it },
                )
            }
        }
    }
}