package com.example.coffe1706.feature.root.presentation

import kotlinx.serialization.Serializable

sealed interface TopLevelDestination {
    @Serializable object Auth : TopLevelDestination
    @Serializable object NearestCoffeeShops : TopLevelDestination
    @Serializable data class CoffeeShop(val locationId: String) : TopLevelDestination
}


