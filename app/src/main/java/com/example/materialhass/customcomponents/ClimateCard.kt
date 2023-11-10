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
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.materialhass.models.Devices

@Composable
fun ClimateCard(device: Devices, modifier: Modifier) {
    OutlinedCard(modifier) {
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
                        "Температура: 24°C",
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
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Default.PlusOne,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Default.Minimize,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    OutlinedIconButton(onClick = { /*TODO*/ }) {
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

@Preview
@Composable
fun card_preview()
{
    ClimateCard(Devices(0, "climate.ac","Кондиционер", "climate", Icons.Default.AcUnit), Modifier.fillMaxWidth())
}