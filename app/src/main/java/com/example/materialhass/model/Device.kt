package com.example.materialhass.model

data class Device(
    val id: Int,
    val name: String,
    val friendly_name: String,
    val type: String,
    val state: String,
    val icon: String,
    val extended_controls: Boolean,
    val brightness: Double?,
    val position: Double?,
    val temperature: Double?
)