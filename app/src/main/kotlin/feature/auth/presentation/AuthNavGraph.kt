package com.example.coffe1706.feature.auth.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.coffe1706.R
import com.example.coffe1706.feature.auth.presentation.LoginRegisterDestination.Login
import com.example.coffe1706.feature.auth.presentation.LoginRegisterDestination.Register
import com.example.coffe1706.feature.auth.presentation.login.LoginScreen
import com.example.coffe1706.feature.auth.presentation.register.RegisterScreen
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.Auth
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.NearestCoffeeShops
import kotlinx.serialization.Serializable

internal sealed interface LoginRegisterDestination {
    @Serializable object Login : LoginRegisterDestination
    @Serializable object Register : LoginRegisterDestination
}

@StringRes
internal fun NavDestination.getAuthNavGraphTile(): Int? = when {
    this.hasRoute<Login>() -> R.string.screen_title_login
    this.hasRoute<Register>() -> R.string.screen_title_register
    else -> null
}

internal fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
    navigation<Auth>(startDestination = Login) {
        composable<Login> { backStackEntry: NavBackStackEntry ->
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(route = Register) {
                        popUpTo(route = Auth) { inclusive = true }
                    }
                },
                onLoginSuccess = {
                    navController.navigate(route = NearestCoffeeShops) {
                        popUpTo(route = Auth) { inclusive = true }
                    }
                },
                animatedVisibilityScope = this@composable,
                sharedTransitionScope = sharedTransitionScope,
            )
        }
        composable<Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Login) {
                        popUpTo(route = Auth) {
                            inclusive = true
                        }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(route = NearestCoffeeShops) {
                        popUpTo(route = Auth) { inclusive = true }
                    }
                },
                animatedVisibilityScope = this@composable,
                sharedTransitionScope = sharedTransitionScope,
            )
        }
    }
}

