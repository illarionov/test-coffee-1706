package com.example.coffe1706.feature.coffeeshop.presentation.menu

import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderItemUiModel

internal sealed interface MenuScreenState {
    object InitialLoad : MenuScreenState
    data class Success(
        val menu: List<OrderItemUiModel>,
    ) : MenuScreenState
    data class LoadError(
        val error: LocalizedMessage
    ) : MenuScreenState
}
