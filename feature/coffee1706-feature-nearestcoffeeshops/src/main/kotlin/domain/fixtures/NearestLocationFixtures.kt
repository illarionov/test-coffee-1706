package com.example.coffe1706.feature.nearestcoffeeshops.domain.fixtures

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.fixtures.LocationFixtures
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance
import com.example.coffe1706.feature.nearestcoffeeshops.domain.NearestLocation

internal object NearestLocationFixtures {
    val bedoefCoffee1 = LocationFixtures.bedoefCoffee1.toNearesLocation(
        CoarseDistance.Companion.kilometers(
            1
        )
    )
    val coffeeLike = LocationFixtures.coffeeLike.toNearesLocation(
        CoarseDistance.Companion.kilometers(
            2
        )
    )
    val emdi = LocationFixtures.emdi.toNearesLocation(CoarseDistance.Companion.kilometers(1))
    val coffeEst = LocationFixtures.coffeEst.toNearesLocation(CoarseDistance.Companion.meters(300))
    val bedoefCoffee2 = LocationFixtures.bedoefCoffee2.toNearesLocation(
        CoarseDistance.Companion.kilometers(
            3
        )
    )

    private fun Location.toNearesLocation(
        distance: CoarseDistance
    ) = NearestLocation(id = this.id, name = this.name, distance = distance)
}
