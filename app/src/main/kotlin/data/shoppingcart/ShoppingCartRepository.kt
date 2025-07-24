package com.example.coffe1706.data.shoppingcart

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    fun shoppingCartFlow(locationId: LocationId): Flow<ShoppingCart>
    suspend fun setShoppingCart(cart: ShoppingCart): Unit
    suspend fun setQuantity(locationId: LocationId, menuItemId: MenuItemId, quantity: Quantity)
    suspend fun clear(locationId: LocationId)
}

