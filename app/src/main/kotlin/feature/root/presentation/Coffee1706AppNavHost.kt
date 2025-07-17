package com.example.coffe1706.feature.root.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffe1706.feature.auth.presentation.authNavGraph
import com.example.coffe1706.feature.coffeeshop.presentation.coffeShopNavGraph
import com.example.coffe1706.feature.coffeeshops.presentation.CoffeeShopsScreen

@Composable
internal fun Coffee1706AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: TopLevelDestination = CoffeeShopDestination,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authNavGraph(navController)
        composable<CoffeeShopsDestination> { CoffeeShopsScreen(onMenuClick = {}, onOrderClick = {}) }
        coffeShopNavGraph(navController)
    }

}
