package com.example.coffe1706.core.model

import java.math.BigDecimal

public data class MenuItem(
    val id: MenuItemId,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
)

@JvmInline
public value class MenuItemId(public val id: Long)
