package com.example.coffe1706.feature.nearestcoffeeshops.presentation.map

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage

internal sealed interface NearestCoffeeShopsMapScreenState {
    object InitialLoad : NearestCoffeeShopsMapScreenState
    data class LoadError(
        val error: LocalizedMessage
    ) : NearestCoffeeShopsMapScreenState
    data class Success(
        val locations: List<Location>,
    ) : NearestCoffeeShopsMapScreenState
}
