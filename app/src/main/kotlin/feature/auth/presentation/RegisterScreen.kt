package com.example.coffe1706.feature.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Text("Register Screen")
        Button(onClick = onRegisterSuccess) { Text("Sign Up") }
    }
}
