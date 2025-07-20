package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.coffe1706.R
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.CoffeeShop
import kotlinx.serialization.Serializable

@Serializable internal object CoffeeShopMenuDestination
@Serializable internal object CoffeeShopOrderDestination

@StringRes
internal fun NavDestination.getCoffeeShopGraphTile(): Int? = when {
    this.hasRoute<CoffeeShopMenuDestination>() -> R.string.screen_title_coffe_shop_menu
    this.hasRoute<CoffeeShopOrderDestination>() -> R.string.screen_title_coffee_shop_order
    else -> null
}

internal fun NavGraphBuilder.coffeShopNavGraph(
    navController: NavHostController
) {
    navigation<CoffeeShop>(startDestination = CoffeeShopMenuDestination) {
        composable<CoffeeShopMenuDestination> { navBackStackEntry ->
            val dest = navBackStackEntry.savedStateHandle.toRoute<CoffeeShop>()
            CoffeeShopMenuScreen(
                items = emptyList(),
                onItemCountChange = { _, _ -> },
                onCheckoutClick = { TODO() },
            )
        }
        composable<CoffeeShopOrderDestination> {
            CoffeeShopOrderScreen(
                items = emptyList(),
                onItemCountChange = { _, _ -> },
                onCheckoutClick = {  },
            )
        }
    }
}

