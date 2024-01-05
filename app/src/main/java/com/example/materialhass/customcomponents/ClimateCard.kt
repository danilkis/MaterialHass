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
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.materialhass.model.Device
import com.example.materialhass.viewmodel.DevicesViewmodel
import kotlinx.coroutines.launch

@Composable
fun ClimateCard(device: Device, modifier: Modifier, viewmodel: DevicesViewmodel) {
    OutlinedCard(modifier) {
        val corutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                DeviceCircle(id = "0", icon = device.icon)
                Spacer(Modifier.width(12.dp))
                Column (Modifier.weight(2f)) {
                    Text(
                        device.friendly_name,
                        style = MaterialTheme.typography.bodyLarge,
                        softWrap = false,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Температура: ${device.temperature}",
                        style = MaterialTheme.typography.bodyMedium,
                        softWrap = false,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        device.state,
                        style = MaterialTheme.typography.bodyMedium,
                        softWrap = false,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    OutlinedIconButton(onClick = { corutineScope.launch { viewmodel.setTemperature(device,
                        device.temperature?.plus(1) ?: 20.0
                    ) } }) {
                        Icon(
                            Icons.Default.PlusOne,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    OutlinedIconButton(onClick = { corutineScope.launch { viewmodel.setTemperature(device,
                        device.temperature?.minus(1) ?: 20.0
                    ) } }) {
                        Icon(
                            Icons.Default.Minimize,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    OutlinedIconButton(onClick = { corutineScope.launch { viewmodel.toggleClimate(device) } }) {
                        Icon(
                            Icons.Default.PowerSettingsNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

