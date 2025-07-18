package com.example.coffe1706.feature.coffeeshop.presentation

import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.MenuItemId
import java.math.BigDecimal

data class OrderCartItem(
    val id: MenuItemId,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
    val quantity: Int,
) {
    init {
        check(quantity >= 0)
    }
}

internal fun MenuItem.withCount(count: Int) = OrderCartItem(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    quantity = count,
)
