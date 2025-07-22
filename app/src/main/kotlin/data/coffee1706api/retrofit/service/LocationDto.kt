package com.example.coffe1706.data.coffee1706api.retrofit.service

import kotlinx.serialization.Serializable

@Serializable
internal data class LocationDto(
    val id: Long,
    val name: String,
    val point: LatLonDto,
)

