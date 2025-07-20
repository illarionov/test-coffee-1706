package com.example.coffe1706.feature.nearestcoffeeshops.presentation

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance

data class NearestLocation(
    val id: LocationId,
    val name: String,
    val distance: CoarseDistance
)
