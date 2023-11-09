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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.PlusOne
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
import androidx.compose.ui.unit.dp
import com.example.materialhass.models.Devices

@Composable
fun ClimateCard(device: Devices)
{
    OutlinedCard() {
        Column (modifier = Modifier
            .padding(8.dp)) {
            Row( horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically)
            {
                DeviceCircle(id = "0", icon = Icons.Default.AcUnit)
                Spacer(Modifier.width(10.dp))
                Column () {
                    Text(device.friendly_name, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(4.dp))
                    Text(device.type, style = MaterialTheme.typography.bodyMedium) //TODO: состояние устройства
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("24C", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.width(10.dp))
                Row(modifier = Modifier
                    .padding(8.dp))
                {
                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f))
                    {
                        OutlinedIconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.PlusOne, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        OutlinedIconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Minimize, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        OutlinedIconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.PowerSettingsNew, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }
        }
    }
}