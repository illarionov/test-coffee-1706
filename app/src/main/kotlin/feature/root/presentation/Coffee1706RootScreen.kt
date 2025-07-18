package com.example.coffe1706.feature.root.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffe1706.core.ui.theme2.Brown40
import com.example.coffe1706.core.ui.theme2.Brown40Dark
import com.example.coffe1706.core.ui.theme2.Coffee1706Theme
import com.example.coffe1706.feature.auth.presentation.authNavGraph
import com.example.coffe1706.feature.coffeeshop.presentation.coffeShopNavGraph
import com.example.coffe1706.feature.coffeeshops.presentation.CoffeeShopsScreen

@Composable
internal fun Coffee1706RootScreen(
    isUserLoggedIn: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = navController.currentDestination?.route ?: "Главная",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .fillMaxWidth()
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        val startDestination = if (isUserLoggedIn) CoffeeShopsDestination else AuthDestination
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            authNavGraph(navController = navController)
            composable<CoffeeShopsDestination> { CoffeeShopsScreen(onMenuClick = {}, onOrderClick = {}) }
            coffeShopNavGraph(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffee1706RootScreen() {
    Coffee1706Theme {
        Coffee1706RootScreen(
            isUserLoggedIn = false,
        )
    }
}
