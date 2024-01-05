package com.example.materialhass.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Room(
    val id: Int,
    val name: String,
    val picture_Url: String?,
    val displayName: String?,
    val icon: String
)