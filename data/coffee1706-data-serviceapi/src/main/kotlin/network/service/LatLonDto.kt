package com.example.coffe1706.data.coffee1706api.retrofit.service

import kotlinx.serialization.Serializable

@Serializable
internal data class LatLonDto(
    val latitude: Double,
    val longitude: Double,
)
