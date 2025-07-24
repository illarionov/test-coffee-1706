package com.example.coffe1706.feature.coffeeshop.presentation.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coffe1706.R
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.map
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.data.shoppingcart.ShoppingCart
import com.example.coffe1706.data.shoppingcart.ShoppingCartRepository
import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepository
import com.example.coffe1706.feature.coffeeshop.presentation.CoffeeShopOrderDestination
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderScreenState.LoadError
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderScreenState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderViewModel @Inject constructor(
    private val menuRepository: CoffeeShopMenuRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val snackbarController: SnackbarController,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val locationId = LocationId(savedStateHandle.toRoute<CoffeeShopOrderDestination>().locationId)

    private val locationMenu: Flow<Response<Map<MenuItemId, MenuItem>>> = flow {
        val items: Response<Map<MenuItemId, MenuItem>> = menuRepository.getMenu(locationId)
            .map { it.associateBy(MenuItem::id) }
        emit(items)
    }.shareIn(viewModelScope, replay = 1, started = SharingStarted.WhileSubscribed())

    /**
     * Храним начальный список заказа, чтобы при снижении количества до 0 элемент не пропадал из списка
     */
    private var initialCartItems: List<MenuItemId>? = null

    val state: StateFlow<OrderScreenState> = locationMenu.combine(
        shoppingCartRepository.shoppingCartFlow(locationId),
    ) { menuItems: Response<Map<MenuItemId, MenuItem>>, shoppingCart: ShoppingCart ->
        when (menuItems) {
            is Response.Success -> {
                val initialItems = initialCartItems ?: shoppingCart.items.keys.toList().also {
                    initialCartItems = it
                }

                val menu = getShoppingCartWithQuantities(
                    menuItems = menuItems.value,
                    initialItems = initialItems,
                    shoppingCart = shoppingCart,
                )
                Success(menu)
            }

            is Response.Failure -> LoadError(menuItems.getCommonErrorMessage())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderScreenState.InitialLoad,
    )

    fun setItemQuantity(id: MenuItemId, newQuantity: Quantity) {
        viewModelScope.launch {
            shoppingCartRepository.setQuantity(locationId, id, newQuantity)
        }
    }

    fun checkout() {
        val orderUiItems = (state.value as? Success)?.menu ?: return
        val itemsText = orderUiItems.joinToString(", ") { "${it.name} (${it.quantity})" }
        viewModelScope.launch {
            snackbarController.enqueueMessage(
                LocalizedMessage(
                    R.string.order_accepted,
                    listOf(itemsText),
                ),
            )
        }
    }

    private companion object {
        private fun getShoppingCartWithQuantities(
            menuItems: Map<MenuItemId, MenuItem>,
            initialItems: List<MenuItemId>,
            shoppingCart: ShoppingCart,
        ): List<OrderItemUiModel> {
            return initialItems.mapNotNull { menuItemId ->
                val menuItem = menuItems[menuItemId] ?: return@mapNotNull null
                val newQuantities: Map<MenuItemId, Quantity> = shoppingCart.items

                OrderItemUiModel(
                    id = menuItem.id,
                    name = menuItem.name,
                    imageUrl = menuItem.imageUrl,
                    price = menuItem.price,
                    quantity = newQuantities[menuItemId] ?: Quantity(0),
                )
            }
        }
    }
}
