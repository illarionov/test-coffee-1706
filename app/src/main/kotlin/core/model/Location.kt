package com.example.coffe1706.core.model

data class Location(
    val id: LocationId,
    val name: String,
    val position: LatLon
)

@JvmInline
public value class LocationId(val id: String)

