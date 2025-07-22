package com.example.coffe1706.data.fixtures

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance.Companion.kilometers
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance.Companion.meters
import com.example.coffe1706.feature.nearestcoffeeshops.domain.NearestLocation

object NearestLocationFixtures {
    val bedoefCoffee1 = LocationFixtures.bedoefCoffee1.toNearesLocation(kilometers(1))
    val coffeeLike = LocationFixtures.coffeeLike.toNearesLocation(kilometers(2))
    val emdi = LocationFixtures.emdi.toNearesLocation(kilometers(1))
    val coffeEst = LocationFixtures.coffeEst.toNearesLocation(meters(300))
    val bedoefCoffee2 = LocationFixtures.bedoefCoffee2.toNearesLocation(kilometers(3))

    private fun Location.toNearesLocation(
        distance: CoarseDistance
    ) = NearestLocation(id = this.id, name = this.name, distance = distance)
}
