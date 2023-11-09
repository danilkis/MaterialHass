package com.example.materialhass.customcomponents

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.materialhass.models.Devices

@Composable
fun LightCard(device: Devices, modifier: Modifier) {

    OutlinedCard(modifier) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DeviceCircle(id = "0", icon = Icons.Default.Light)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(device.friendly_name, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        device.type, style = MaterialTheme.typography.bodyMedium
                    ) //TODO: состояние устройства
                }
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedIconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.PowerSettingsNew, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
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

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun LightCardPreview()
{
    FlowRow(
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 2
    ) {
        LightCard(device = Devices(0, "light", "Lustra", "light"), Modifier.weight(0.5f))
        LightCard(device = Devices(0, "light", "Lustra2", "light"), Modifier.weight(0.5f))
        LightCard(device = Devices(0, "light", "Lustra3", "light"), Modifier.fillMaxWidth())
    }
}