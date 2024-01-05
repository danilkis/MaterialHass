package com.example.materialhass.customcomponents

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.materialhass.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoomCard(room: Room, navController: NavController, onLongClick: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(
            onClick =
            {
                coroutineScope.launch(Dispatchers.Main) {
                    Log.e("NAVIGATING TO", room.toString())
                    navController.navigate("room/${room.id}")
                }
            },
            onLongClick = { onLongClick() },
        )) {
        Column(modifier = Modifier.height(230.dp))
        {
            Image(
                painter = rememberAsyncImagePainter(room.picture_Url),
                contentDescription = null,
                modifier = Modifier
                    .height(170.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                DeviceCircle(id = "",icon = room.icon)
                Spacer(Modifier.width(10.dp))
                Column {
                    if(room.displayName.isNullOrBlank())
                    {
                        Text(room.name)
                    }
                    else
                    {
                        Text(room.displayName)
                    }
                }
            }
        }
    }
}
