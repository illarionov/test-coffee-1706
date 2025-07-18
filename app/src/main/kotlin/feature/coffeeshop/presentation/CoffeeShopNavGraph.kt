package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.CoffeeShop
import kotlinx.serialization.Serializable

@Serializable internal object CoffeeShopMenuDestination
@Serializable internal object CoffeeShopOrderDestination

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

