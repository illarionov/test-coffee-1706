package com.example.coffe1706.core.model

import java.math.BigDecimal

data class MenuItem(
    val id: MenuItemId,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
)

@JvmInline
public value class MenuItemId(val id: Long)
