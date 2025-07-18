package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CoffeeShopMenuScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text("Menu Screen")
        Button(onClick = onBack) { Text("Back") }
    }
}
