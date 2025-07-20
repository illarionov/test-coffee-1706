package com.example.coffe1706.core.ui.theme3

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.example.coffe1706.core.ui.internationalization.formatter.distance.LocalCoarseDistanceFormatter
import com.example.coffe1706.core.ui.internationalization.formatter.distance.rememberLocalCoarseDistanceFormatter
import com.example.coffe1706.core.ui.internationalization.formatter.price.LocalPriceFormatter
import com.example.coffe1706.core.ui.internationalization.formatter.price.rememberLocalPriceFormatter

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
    surfaceVariant = Coffee1706Colors.SurfaceContainerSecondary,
    surfaceContainer = Coffee1706Colors.SurfaceContainer,
    onSurface = Coffee1706Colors.TextColor,
    onSurfaceVariant = Coffee1706Colors.TextColorLighter,
)

private val Coffee1706Shapes = Shapes(
    small = RoundedCornerShape(5.0.dp)
)

@Composable
fun Coffee1706Theme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCoarseDistanceFormatter providesDefault rememberLocalCoarseDistanceFormatter(),
        LocalPriceFormatter providesDefault rememberLocalPriceFormatter()
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Coffee1706Typography.materialTypography,
            shapes = Coffee1706Shapes,
            content = content
        )
    }
}
