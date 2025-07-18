package com.example.coffe1706.feature.auth.presentation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.coffe1706.feature.auth.presentation.LoginRegisterDestination.Login
import com.example.coffe1706.feature.auth.presentation.LoginRegisterDestination.Register
import com.example.coffe1706.feature.root.presentation.TopLevelDestination.Auth
import kotlinx.serialization.Serializable

internal sealed interface LoginRegisterDestination {
    @Serializable object Login : LoginRegisterDestination
    @Serializable object Register : LoginRegisterDestination
}


internal fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
    navigation<Auth>(startDestination = Login) {
        composable<Login> { backStackEntry: NavBackStackEntry ->
            LoginScreen(
                onLogin = {},
                onNavigateToRegister = {
                    navController.navigate(route = Register) {
                        popUpTo(route = Auth) { inclusive = true }
                    }
                },
                animatedVisibilityScope = this@composable,
                sharedTransitionScope = sharedTransitionScope,
            )
        }
        composable<Register> {
            RegisterScreen(
                onSendRegisterForm = {},
                onNavigateToLogin = {
                    navController.navigate(route = Login) {
                        popUpTo(route = Auth) {
                            inclusive = true
                        }
                    }
                },
                animatedVisibilityScope = this@composable,
                sharedTransitionScope = sharedTransitionScope,
            )
        }
    }
}

