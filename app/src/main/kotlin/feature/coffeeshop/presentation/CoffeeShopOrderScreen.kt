package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CoffeeShopOrderScreen(onBack: () -> Unit) {
    Column {
        Text("Your Order")
        Button(onClick = onBack) { Text("Back") }
    }
}
