package com.example.coffe1706.feature.root.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffe1706.R
import com.example.coffe1706.core.authmanager.AuthManager
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.core.ui.component.snackbar.subscribeToSnackbarsFlowWithLifecycle
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.feature.auth.presentation.authNavGraph
import com.example.coffe1706.feature.auth.presentation.getAuthNavGraphTile
import com.example.coffe1706.feature.coffeeshop.presentation.coffeShopNavGraph
import com.example.coffe1706.feature.coffeeshop.presentation.getCoffeeShopGraphTile
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.NearestCoffeeShopsScreen
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.Auth
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.CoffeeShop
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.NearestCoffeeShops
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun Coffee1706RootScreen(
    authManager: AuthManager,
    snackbarController: SnackbarController,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    // TODO: wrong
    val isUserLoggedIn: Boolean by authManager.isUserLoggedIn.collectAsStateWithLifecycle(
        initialValue = false,
    )

    val snackbarHostState = remember { SnackbarHostState() }
    subscribeToSnackbarsFlowWithLifecycle(snackbarController, snackbarHostState)

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(elevation = 4.dp),
                title = {
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val titleResId = backStackEntry?.destination.getNavDestinationTitle()
                    val title = if (titleResId != null) {
                        stringResource(titleResId)
                    } else {
                        ""
                    }
                    Text(
                        text = title,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
    ) { paddingValues ->
        SharedTransitionLayout {
            val startDestination = if (isUserLoggedIn) NearestCoffeeShops else Auth
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                authNavGraph(
                    navController = navController,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
                composable<NearestCoffeeShops> {
                    NearestCoffeeShopsScreen(
                        onLocationClick = { locationId: LocationId ->
                            navController.navigate(route = CoffeeShop(locationId.id))
                        },
                        onShowOnMapClick = { /* TODO */ },
                        locations = emptyList(),
                    )
                }
                coffeShopNavGraph(navController = navController)
            }
        }
    }
}

@StringRes
private fun NavDestination?.getNavDestinationTitle(): Int? {
    return when  {
        this == null -> null
        this.hasRoute<NearestCoffeeShops>() -> R.string.screen_title_nearest_coffee_shops
        else -> listOf(
            ::getAuthNavGraphTile,
            ::getCoffeeShopGraphTile
        ).firstNotNullOfOrNull { titleFunction ->
            titleFunction()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffee1706RootScreen() {
    Coffee1706Theme {
        Coffee1706RootScreen(
            authManager = object : AuthManager {
                override val isUserLoggedIn = flowOf(false)
            },
            snackbarController = SnackbarController()
        )
    }
}
