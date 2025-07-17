package com.example.coffe1706.core.model

import java.math.BigDecimal

data class LocationMenuItem(
    val id: LocationMenuItemId,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
)

@JvmInline
public value class LocationMenuItemId(val id: String)
