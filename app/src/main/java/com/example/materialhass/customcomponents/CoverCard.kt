package com.example.materialhass.customcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Blinds
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Upload
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CoverCard(devices: com.example.materialhass.models.Devices)
{
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically)
            {
                Row(modifier = Modifier.weight(1f))
                {
                    DeviceCircle(id = "0", icon = Icons.Default.Blinds)
                    Spacer(Modifier.width(10.dp))
                    Column() {
                        Text(devices.friendly_name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(4.dp))
                        Text(devices.type, style = MaterialTheme.typography.bodyMedium) //TODO: состояние устройства
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End)
                {
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Upload, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Stop, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Download, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
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

