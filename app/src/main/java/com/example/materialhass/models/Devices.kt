package com.example.materialhass.models

import androidx.compose.ui.graphics.vector.ImageVector

data class Devices(
    val id: Int,
    val name: String,
    val friendly_name: String,
    val type: String,
    val state: String,
    val icon: ImageVector,
    val extended_controls: Boolean,
    val brightness: Double?,
    val position: Double?,
    val temperature: Double?
)