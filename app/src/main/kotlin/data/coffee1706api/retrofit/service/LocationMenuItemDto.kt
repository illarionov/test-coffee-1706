package com.example.coffe1706.data.coffee1706api.retrofit.service

import kotlinx.serialization.SerialName
import java.math.BigDecimal

internal data class LocationMenuItemDto(
    val id: String,
    val name: String,
    @SerialName("imageURL") val imageUrl: String,
    val price: BigDecimal,
)
