package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.coffe1706.feature.root.presentation.CoffeeShopDestination
import kotlinx.serialization.Serializable

@Serializable internal object CoffeeShopMenuDestination
@Serializable internal object CoffeeShopOrderDestination

internal fun NavGraphBuilder.coffeShopNavGraph(
    navController: NavHostController
) {
    navigation<CoffeeShopDestination>(startDestination = CoffeeShopMenuDestination) {
        composable<CoffeeShopMenuDestination> { CoffeeShopMenuScreen(onBack = {}) }
        composable<CoffeeShopOrderDestination> { CoffeeShopOrderScreen {  } }
    }
}

