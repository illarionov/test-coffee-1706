package com.example.coffe1706.feature.root.presentation

import kotlinx.serialization.Serializable

sealed interface TopLevelDestination

@Serializable object AuthDestination : TopLevelDestination
@Serializable object CoffeeShopsDestination : TopLevelDestination
@Serializable object CoffeeShopDestination : TopLevelDestination

