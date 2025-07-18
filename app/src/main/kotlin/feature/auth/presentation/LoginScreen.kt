package com.example.coffe1706.feature.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun LoginScreen(
    onRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text("Login Screen")
        Button(onClick = onLoginSuccess) { Text("Login") }
        TextButton(onClick = onRegister) { Text("Register") }
    }
}
