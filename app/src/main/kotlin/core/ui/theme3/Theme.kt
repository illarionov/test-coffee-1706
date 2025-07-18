package com.example.coffe1706.core.ui.theme3

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = Coffee1706Colors.ColorPrimary,
    onPrimary = Coffee1706Colors.TextColorOnPrimary,
    primaryContainer = Brown40,
    onPrimaryContainer = Coffee1706Colors.TextColorOnPrimary,
    secondary = Coffee1706Colors.SurfaceContainer,
    onSecondary = Coffee1706Colors.TextColor,
    onTertiary = Coffee1706Colors.TextColor,
    background = Coffee1706Colors.Background,
    onBackground = Coffee1706Colors.TextColor,
    surface = Coffee1706Colors.Background,
    surfaceContainer = Coffee1706Colors.SurfaceContainer,
    onSurface = Coffee1706Colors.TextColor,
    onSurfaceVariant = Coffee1706Colors.TextColorLighter
)

@Composable
fun Coffee1706Theme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
