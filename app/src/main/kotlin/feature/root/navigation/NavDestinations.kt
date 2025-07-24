package com.example.coffe1706.feature.root.navigation

import kotlinx.serialization.Serializable

sealed interface TopLevelDestination {
    @Serializable object Auth : TopLevelDestination
    @Serializable object NearestCoffeeShops : TopLevelDestination
}

@Serializable object NearestCoffeeShopsMap

@Serializable data class CoffeeShop(val locationId: String)
