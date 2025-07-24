package com.example.coffe1706.data.shoppingcart

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import kotlinx.coroutines.flow.Flow

public interface ShoppingCartRepository {
    public fun shoppingCartFlow(locationId: LocationId): Flow<ShoppingCart>
    public suspend fun setShoppingCart(cart: ShoppingCart)
    public suspend fun setQuantity(locationId: LocationId, menuItemId: MenuItemId, quantity: Quantity)
    public suspend fun clear(locationId: LocationId)
}

