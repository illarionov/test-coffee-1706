package com.example.coffe1706.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
