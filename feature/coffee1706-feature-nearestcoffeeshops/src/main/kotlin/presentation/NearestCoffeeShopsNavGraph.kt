package com.example.coffe1706.feature.nearestcoffeeshops.presentation

import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.feature.nearestcoffeeshops.R
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.list.NearestCoffeeShopsScreen
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.map.NearestCoffeShopsMapScreen
import com.example.coffe1706.feature.root.navigation.CoffeeShop
import com.example.coffe1706.feature.root.navigation.NearestCoffeeShopsMap
import com.example.coffe1706.feature.root.navigation.TopLevelDestination.NearestCoffeeShops

@StringRes
public fun NavDestination.getNearestCoffeeShopsNavGraphTile(): Int? = when {
    hasRoute<NearestCoffeeShops>() -> R.string.screen_title_nearest_coffee_shops
    hasRoute<NearestCoffeeShopsMap>() -> R.string.screen_title_nearest_coffee_shops_map
    else -> null
}

public fun NavGraphBuilder.nearestCoffeeShopsNavGraph(
    navController: NavHostController,
) {
    composable<NearestCoffeeShops> {
        NearestCoffeeShopsScreen(
            onLocationClick = { locationId: LocationId ->
                navController.navigate(route = CoffeeShop(locationId.id))
            },
            onShowOnMapClick = {
                navController.navigate(route = NearestCoffeeShopsMap)
            },
        )
    }
    composable<NearestCoffeeShopsMap> {
        NearestCoffeShopsMapScreen(
            onLocationClick = { locationId: LocationId ->
                navController.navigate(route = CoffeeShop(locationId.id))
            },
        )
    }
}
