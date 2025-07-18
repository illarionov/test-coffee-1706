package com.example.coffe1706.feature.coffeeshops.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CoffeeShopsScreen(
    onMenuClick: () -> Unit,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text("Nearby Coffee Shops")
        Button(onClick = onMenuClick) { Text("Menu") }
        Button(onClick = onOrderClick) { Text("Your Order") }
    }
}
