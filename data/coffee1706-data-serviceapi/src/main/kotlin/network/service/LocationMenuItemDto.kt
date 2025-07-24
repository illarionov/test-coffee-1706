@file:OptIn(ExperimentalSerializationApi::class)

package com.example.coffe1706.data.coffee1706api.retrofit.service

import com.example.coffe1706.data.coffee1706api.util.BigDecimalSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
internal data class LocationMenuItemDto(
    val id: Long,
    val name: String,
    @SerialName("imageURL") val imageUrl: String,
    @Serializable(with = BigDecimalSerializer::class) val price: BigDecimal,
)
