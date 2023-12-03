package com.example.materialhass.API

data class Entity(
    val entity_id: String,
    val state: String,
    val attributes: Map<String, Any>
)
