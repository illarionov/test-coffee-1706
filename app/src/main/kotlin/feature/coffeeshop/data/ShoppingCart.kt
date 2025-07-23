package com.example.coffe1706.feature.coffeeshop.data

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity

data class ShoppingCart(
    val locationId: LocationId,
    val items: Map<MenuItemId, Quantity>,
)
