package com.example.coffe1706.feature.coffeeshop.presentation.order

import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage

internal sealed interface OrderScreenState  {
    object InitialLoad : OrderScreenState
    data class Success(
        val menu: List<OrderItemUiModel>,
    ) : OrderScreenState
    data class LoadError(
        val errorMessage: LocalizedMessage
    ) : OrderScreenState
}
