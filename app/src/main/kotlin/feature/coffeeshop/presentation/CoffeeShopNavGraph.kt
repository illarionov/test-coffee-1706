package com.example.coffe1706.feature.coffeeshop.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.coffe1706.R
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.feature.coffeeshop.presentation.menu.CoffeeShopMenuScreen
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderScreen
import com.example.coffe1706.feature.root.presentation.CoffeeShop
import com.example.coffe1706.feature.root.presentation.TopLevelDestination
import kotlinx.serialization.Serializable

@Serializable
internal object CoffeeShopMenuDestination

@Serializable
internal data class CoffeeShopOrderDestination(val locationId: String)

@StringRes
internal fun NavDestination.getCoffeeShopGraphTile(): Int? = when {
    this.hasRoute<CoffeeShopMenuDestination>() -> R.string.screen_title_coffe_shop_menu
    this.hasRoute<CoffeeShopOrderDestination>() -> R.string.screen_title_coffee_shop_order
    else -> null
}

internal fun NavGraphBuilder.coffeeShopNavGraph(
    navController: NavHostController,
    onMenuClosed: (LocationId) -> Unit,
) {
    navigation<CoffeeShop>(
        startDestination = CoffeeShopMenuDestination,
    ) {
        composable<CoffeeShopMenuDestination> { navBackStackEntry: NavBackStackEntry ->
            val locationId: String = navBackStackEntry.savedStateHandle.toRoute<CoffeeShop>().locationId
            CoffeeShopMenuScreen(
                onCheckout = {
                    navController.navigate(route = CoffeeShopOrderDestination(locationId))
                },
            )

            LaunchedEffect(locationId) {
                val observer = object : LifecycleEventObserver {
                    override fun onStateChanged(
                        source: LifecycleOwner,
                        event: Lifecycle.Event,
                    ) {
                        if (event == Lifecycle.Event.ON_DESTROY) {
                            navBackStackEntry.lifecycle.removeObserver(this)
                            onMenuClosed(LocationId(locationId))
                            Log.v("APP", "coffee shop ${locationId} closed")
                        }
                    }
                }

                navBackStackEntry.lifecycle.addObserver(observer)
            }
        }
        composable<CoffeeShopOrderDestination> {
            OrderScreen(
                onComplete = {
                    navController.navigate(route = TopLevelDestination.NearestCoffeeShops) {
                        launchSingleTop = true
                        popUpTo(TopLevelDestination.NearestCoffeeShops) {
                            inclusive = false
                        }
                    }
                },
            )
        }
    }
}

