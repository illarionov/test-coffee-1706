package com.example.coffe1706

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.core.ui.component.snackbar.subscribeToSnackbarFlowWithLifecycle
import com.example.coffe1706.core.ui.theme.Coffee1706Theme
import com.example.coffe1706.data.coffee1706api.session.Coffee1706AuthManager
import com.example.coffe1706.data.shoppingcart.ShoppingCartRepository
import com.example.coffe1706.feature.auth.presentation.authNavGraph
import com.example.coffe1706.feature.auth.presentation.getAuthNavGraphTile
import com.example.coffe1706.feature.coffeeshop.presentation.coffeeShopNavGraph
import com.example.coffe1706.feature.coffeeshop.presentation.getCoffeeShopGraphTile
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.getNearestCoffeeShopsNavGraphTile
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.nearestCoffeeShopsNavGraph
import com.example.coffe1706.feature.root.navigation.TopLevelDestination
import com.example.coffe1706.feature.root.navigation.TopLevelDestination.Auth
import com.example.coffe1706.feature.root.navigation.TopLevelDestination.NearestCoffeeShops
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch

@Composable
internal fun Coffee1706Screen(
    authManager: Coffee1706AuthManager,
    snackbarController: SnackbarController,
    modifier: Modifier = Modifier,
    shoppingCartRepository: ShoppingCartRepository? = null,
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    subscribeToSnackbarFlowWithLifecycle(snackbarController, snackbarHostState)

    LaunchedEffect(authManager) {
        authManager.isUserLoggedIn
            .runningFold(Pair<Boolean?, Boolean?>(null, null)) { old, new -> old.second to new }
            .collect { (old, new) ->
                if (old == true && new == false) {
                    navController.navigate(route = Auth) {
                        launchSingleTop = true
                        popUpTo(NearestCoffeeShops) { inclusive = true }
                    }
                }
            }
    }

    val isUserLoggedIn: Boolean? by authManager.isUserLoggedIn.collectAsState(initial = null)
    val currentUserLoggedIn = isUserLoggedIn
    if (currentUserLoggedIn != null) {
        val startDestination = if (currentUserLoggedIn) {
            NearestCoffeeShops
        } else {
            Auth
        }

        Scaffold(
            modifier = modifier,
            navController = navController,
            snackbarHostState = snackbarHostState,
            shoppingCartRepository = shoppingCartRepository,
            startDestination = startDestination,
        )
    } else {
        /* Content loading Placeholder */
    }
}

@Composable
private fun Scaffold(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    shoppingCartRepository: ShoppingCartRepository?,
    startDestination: TopLevelDestination,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(navController)
        },
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.imePadding(),
            )
        },
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()

        SharedTransitionLayout {
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
                nearestCoffeeShopsNavGraph(navController)
                coffeeShopNavGraph(
                    navController = navController,
                    onMenuClosed = { locationId ->
                        coroutineScope.launch {
                            shoppingCartRepository?.clear(locationId)
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun TopAppBar(navController: NavHostController) {
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
        navigationIcon = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentEntry = backStackEntry
            if (currentEntry != null && navController.previousBackStackEntry != null) {
                IconButton(
                    onClick = {
                        navController.navigate(route = NearestCoffeeShops) {
                            launchSingleTop = true
                            popUpTo(route = NearestCoffeeShops) {
                                inclusive = false
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.button_navigate_up_content_description),
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

@StringRes
private fun NavDestination?.getNavDestinationTitle(): Int? {
    return when {
        this == null -> null
        else -> listOf(
            ::getNearestCoffeeShopsNavGraphTile,
            ::getAuthNavGraphTile,
            ::getCoffeeShopGraphTile,
        ).firstNotNullOfOrNull { titleFunction ->
            titleFunction()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffee1706RootScreen() {
    Coffee1706Theme {
        Coffee1706Screen(
            authManager = object : Coffee1706AuthManager {
                override val isUserLoggedIn = flowOf(false)
            },
            snackbarController = SnackbarController(),
            shoppingCartRepository = null,
        )
    }
}
