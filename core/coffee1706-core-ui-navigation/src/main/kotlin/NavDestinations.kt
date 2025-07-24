package com.example.coffe1706.feature.root.navigation

import kotlinx.serialization.Serializable

public sealed interface TopLevelDestination {
    @Serializable
    public object Auth : TopLevelDestination

    @Serializable
    public object NearestCoffeeShops : TopLevelDestination
}

@Serializable
public object NearestCoffeeShopsMap

@Serializable
public data class CoffeeShop(val locationId: String)
