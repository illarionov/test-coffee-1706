package com.example.coffe1706.data.shoppingcart

import android.util.Log
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация репозитория с корзиной с хранением в памяти для тестов
 */
@Singleton
class InMemoryShoppingCartRepository @Inject constructor() : ShoppingCartRepository {
    private val cartMap: MutableMap<LocationId, MutableStateFlow<ShoppingCart>> =
        ConcurrentHashMap()

    override fun shoppingCartFlow(locationId: LocationId): Flow<ShoppingCart> {
        val flow = cartMap.computeIfAbsent(locationId) {
            MutableStateFlow(ShoppingCart(locationId, emptyMap()))
        }
        return flow
    }

    override suspend fun setShoppingCart(cart: ShoppingCart) {
        val flow = cartMap.computeIfAbsent(cart.locationId) { MutableStateFlow(cart) }
        flow.value = cart
    }

    override suspend fun setQuantity(locationId: LocationId, menuItemId: MenuItemId, quantity: Quantity) {
        val flow = cartMap.computeIfAbsent(locationId) {
            MutableStateFlow(
                ShoppingCart(
                    locationId,
                    emptyMap()
                )
            )
        }
        // TODO: lock?
        val currentCart = flow.value
        val updatedItems = if (quantity.value > 0) {
            currentCart.items + (menuItemId to quantity)
        } else {
            currentCart.items - menuItemId
        }
        flow.value = currentCart.copy(items = updatedItems)
    }

    override suspend fun clear(locationId: LocationId) {
        val pred = cartMap.remove(locationId)
        if (pred != null) {
            pred.value = ShoppingCart(locationId, emptyMap())
            if (pred.subscriptionCount.value > 0) {
                Log.e("Coffee1706", "Cart removed while subscriptionCount > 0")
            }
        }
    }
}
