package com.example.coffe1706.data.shoppingcart

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.data.shoppingcart.dto.ShoppingCartDto
import com.example.coffe1706.data.shoppingcart.dto.ShoppingCartsDto
import com.example.coffe1706.data.shoppingcart.dto.copy
import com.example.coffe1706.data.shoppingcart.dto.shoppingCartDto
import com.example.coffe1706.data.shoppingcart.dto.shoppingCartsDto
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * Реализация репозитория с корзиной на основе Datastore protobuf
 */
class DatastoreShoppingCartRepository @Inject constructor (
    private val dataStore: DataStore<ShoppingCartsDto>
) : ShoppingCartRepository {
    override fun shoppingCartFlow(locationId: LocationId): Flow<ShoppingCart> {
        return dataStore.data.map { dtos ->
            dtos.getCartsOrDefault(
                /* key = */ locationId.id,
                /* defaultValue = */ shoppingCartDto { this.locationId = locationId.id }
            ).toShoppingCart()
        }
    }

    override suspend fun setShoppingCart(cart: ShoppingCart) {
        dataStore.updateData { dtos ->
            dtos.copy {
                this.carts.put(cart.locationId.id, cart.toShoppingCartDto())
            }
        }
    }

    override suspend fun setQuantity(
        locationId: LocationId,
        menuItemId: MenuItemId,
        quantity: Quantity,
    ) {
        dataStore.updateData { dtos: ShoppingCartsDto ->
            dtos.copy {
                val cart = this.carts[locationId.id] ?: shoppingCartDto { this.locationId = locationId.id }
                val newCart = cart.copy {
                    if (quantity.value != 0) {
                        items.put(menuItemId.id, quantity.value)
                    } else {
                        items.remove(menuItemId.id)
                    }
                }
                this.carts.put(locationId.id, newCart)
            }
        }
    }

    override suspend fun clear(locationId: LocationId) {
        dataStore.updateData { dtos: ShoppingCartsDto ->
            dtos.copy {
                this.carts.remove(locationId.id)
            }
        }
    }

    companion object {
        fun ShoppingCartDto.toShoppingCart(): ShoppingCart {
            return ShoppingCart(
                locationId = LocationId(this.locationId),
                items = this.itemsMap.map { (menuItemId, quantity) ->
                    MenuItemId(menuItemId) to Quantity(quantity)
                }.toMap()
            )
        }

        fun ShoppingCart.toShoppingCartDto(): ShoppingCartDto = shoppingCartDto {
            val src: ShoppingCart = this@toShoppingCartDto
            this.locationId = src.locationId.id
            src.items.forEach { (menuItemId, quantity) ->
                this.items.put(menuItemId.id, quantity.value)
            }
        }
    }

    object ShoppingCartsDtoSerializer : Serializer<ShoppingCartsDto> {
        override val defaultValue: ShoppingCartsDto = shoppingCartsDto { }

        override suspend fun readFrom(input: InputStream): ShoppingCartsDto {
            try {
                return ShoppingCartsDto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: ShoppingCartsDto, output: OutputStream) = t.writeTo(output)
    }

}
