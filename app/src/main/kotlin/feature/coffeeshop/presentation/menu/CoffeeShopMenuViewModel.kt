package com.example.coffe1706.feature.coffeeshop.presentation.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.data.shoppingcart.ShoppingCart
import com.example.coffe1706.data.shoppingcart.ShoppingCartRepository
import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepository
import com.example.coffe1706.feature.coffeeshop.presentation.menu.MenuScreenState.LoadError
import com.example.coffe1706.feature.coffeeshop.presentation.menu.MenuScreenState.Success
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderItemUiModel
import com.example.coffe1706.feature.root.presentation.CoffeeShop
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
internal class CoffeeShopMenuViewModel @Inject constructor(
    private val menuRepository: CoffeeShopMenuRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val locationId = LocationId(savedStateHandle.toRoute<CoffeeShop>().locationId)

    private val locationMenu: Flow<Response<List<MenuItem>>> = flow {
        emit(menuRepository.getMenu(locationId))
    }.shareIn(viewModelScope, replay = 1, started = SharingStarted.WhileSubscribed())

    val state: StateFlow<MenuScreenState> = locationMenu.combine(
        shoppingCartRepository.shoppingCartFlow(locationId),
    ) { menuItems: Response<List<MenuItem>>, shoppingCart: ShoppingCart ->
        when (menuItems) {
            is Response.Success -> {
                val menu = getMenuWithQuantities(menuItems.value, shoppingCart)
                Success(menu)
            }

            is Response.Failure -> LoadError(menuItems.getCommonErrorMessage())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MenuScreenState.InitialLoad,
    )

    fun setItemQuantity(id: MenuItemId, newQuantity: Quantity) {
        viewModelScope.launch {
            shoppingCartRepository.setQuantity(locationId, id, newQuantity)
        }
    }

    private companion object {
        private fun getMenuWithQuantities(
            menuItems: List<MenuItem>,
            shoppingCart: ShoppingCart,
        ): List<OrderItemUiModel> {
            return menuItems.map {
                OrderItemUiModel(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    quantity = shoppingCart.items.getOrElse(it.id) { Quantity(0) },
                )
            }
        }
    }
}
