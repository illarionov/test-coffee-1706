package com.example.coffe1706.core.ui.internationalization.formatter.distance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * CompositionLocal для проброса форматтера расстояний
 */
public val LocalCoarseDistanceFormatter : ProvidableCompositionLocal<CoarseDinstanceFormatter> = staticCompositionLocalOf { error("LocalCoarseDinstanceFormatter not initialized") }

@Composable
public fun rememberLocalCoarseDistanceFormatter(): CoarseDinstanceFormatter {
    val resources = LocalContext.current.resources
    return remember { CoarseDinstanceFormatter(resources) }
}

@Composable
@ReadOnlyComposable
public fun localizedMessage(distance: CoarseDistance): String = LocalCoarseDistanceFormatter.current.format(distance)
