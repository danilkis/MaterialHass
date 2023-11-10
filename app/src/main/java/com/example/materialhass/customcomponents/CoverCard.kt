package com.example.materialhass.customcomponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun CoverCard(devices: com.example.materialhass.models.Devices, modifier: Modifier) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Log.d("Test", "${devices.friendly_name} - $size")

    OutlinedCard(modifier.onSizeChanged { size = it }) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Row()
                {
                    DeviceCircle(id = "0", icon = devices.icon)
                    Spacer(Modifier.width(10.dp))
                    Column() { //modifier = Modifier.weight(1f)
                        Text(devices.friendly_name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            devices.type,
                            style = MaterialTheme.typography.bodyMedium
                        ) //TODO: состояние устройства
                    }

                    if (size.width > 700){
                        ManagementOutlinedIconButtons(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (size.width < 700){
                ManagementOutlinedIconButtons(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
            }else{
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

}



@Composable
fun ManagementOutlinedIconButtons(
    modifier: Modifier,
    verticalAlignment: Alignment.Vertical,
    horizontalArrangement: Arrangement.Horizontal
){
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    )
    {
        OutlinedIconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Default.Upload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        OutlinedIconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Default.Stop,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        OutlinedIconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Default.Download,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}



