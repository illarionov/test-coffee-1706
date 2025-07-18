package com.example.coffe1706.core.ui.theme2

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lightOnBackgroundVariant = Brown30

private val LightColorScheme = lightColors(
    primary = Gray10,
    primaryVariant = Gray70,
    onPrimary = Brown40,
    secondary = Brown40Dark,
    secondaryVariant = Brown40Dark,
    background = Color.White,
    surface = Brown20,
    onSecondary = Brown20,
    onBackground = Brown40,
    onSurface = Brown40,
    onError = Brown20,
)

@Composable
fun Coffee1706Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorScheme,
        shapes = Coffee1706Shapes,
        typography = coffee1706Typography,
        content = content
    )
}
