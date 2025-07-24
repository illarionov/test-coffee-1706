package com.example.coffe1706.feature.coffeeshop.presentation.order

import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import java.math.BigDecimal

internal data class OrderItemUiModel(
    val id: MenuItemId,
    val name: String,
    val imageUrl: String,
    val price: BigDecimal,
    val quantity: Quantity,
)

internal fun MenuItem.withQuantity(count: Int) = OrderItemUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    quantity = Quantity(count),
)
