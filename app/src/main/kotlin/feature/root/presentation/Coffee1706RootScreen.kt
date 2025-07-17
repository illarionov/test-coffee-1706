package com.example.coffe1706.feature.root.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun Coffee1706RootScreen(
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = if (isLoggedIn) CoffeeShopsDestination else AuthDestination
    val snackbarHostState = remember { SnackbarHostState() }

}


@Preview(showBackground = true)
@Composable
private fun PreviewCoffee1706RootScreen() {
    Coffee1706RootScreen(true)
}
