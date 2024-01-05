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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.materialhass.model.Device
import com.example.materialhass.viewmodel.DevicesViewmodel
import kotlinx.coroutines.launch

@Composable
fun CoverCard(device: Device, modifier: Modifier, viewmodel: DevicesViewmodel) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Log.d("Test", "${device.friendly_name} - $size")
    val corutineScope = rememberCoroutineScope()

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
                    DeviceCircle(id = "0", icon = device.icon)
                    Spacer(Modifier.width(10.dp))
                    Column { //modifier = Modifier.weight(1f)
                        Text(device.friendly_name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            device.state,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    if (size.width > 700){
                        ManagementOutlinedIconButtons(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            viewmodel,
                            device
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (!device.extended_controls){
                ManagementOutlinedIconButtons(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    viewmodel,
                    device
                )
            }else{
                Column {
                    var sliderPosition by remember { mutableStateOf(0f) }
                    sliderPosition = device.position!!.toFloat()
                    Slider(
                        value = sliderPosition,
                        valueRange = 0f..100f,
                        onValueChange = {
                            val position_value = it.toInt() // Convert slider position to brightness value
                            sliderPosition = it
                            corutineScope.launch {
                                viewmodel.setPosition(device, position_value)
                            } // Invoke the function on brightness change
                        },
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
    horizontalArrangement: Arrangement.Horizontal,
    viewmodel: DevicesViewmodel,
    device: Device
){
    val courtineScope = rememberCoroutineScope()
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    )
    {
        OutlinedIconButton(onClick = { courtineScope.launch { viewmodel.openCover(device) } }) {
            Icon(
                Icons.Default.Upload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        OutlinedIconButton(onClick = { courtineScope.launch { viewmodel.stopCover(device) }  }) {
            Icon(
                Icons.Default.Stop,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        OutlinedIconButton(onClick = { courtineScope.launch { viewmodel.closeCover(device) }  }) {
            Icon(
                Icons.Default.Download,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}



