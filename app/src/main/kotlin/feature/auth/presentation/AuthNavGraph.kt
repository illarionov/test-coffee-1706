package com.example.coffe1706.feature.auth.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.coffe1706.feature.root.presentation.AuthDestination
import kotlinx.serialization.Serializable


@Serializable internal object LoginDestination
@Serializable internal object RegisterDestination

internal fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<AuthDestination>(startDestination = LoginDestination) {
        composable<LoginDestination> { LoginScreen(onLoginSuccess = {}, onRegister = {}) }
        composable<RegisterDestination> { RegisterScreen(onRegisterSuccess = {}) }
    }
}

