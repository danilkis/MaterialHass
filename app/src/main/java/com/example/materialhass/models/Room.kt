package com.example.materialhass.models

import androidx.compose.ui.graphics.vector.ImageVector

data class Room(
    val id: Int,
    val name: String,
    val picture_Url: String?,
    val icon: ImageVector
)