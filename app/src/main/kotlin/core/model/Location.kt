package com.example.coffe1706.core.model

import kotlinx.serialization.Serializable

data class Location(
    val id: LocationId,
    val name: String,
    val position: LatLon
)

@JvmInline
@Serializable
public value class LocationId(val id: String)

