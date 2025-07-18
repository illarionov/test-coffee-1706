package com.example.coffe1706.core.ui.design.formatter.distance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * CompositionLocal для проброса форматтера расстояний
 */
val LocalCoarseDistanceFormatter : ProvidableCompositionLocal<CoarseDinstanceFormatter> = staticCompositionLocalOf { error("LocalCoarseDinstanceFormatter not initialized") }

@Composable
fun rememberLocalCoarseDistanceFormatter(): CoarseDinstanceFormatter {
    val resources = LocalContext.current.resources
    return remember { CoarseDinstanceFormatter(resources) }
}

@Composable
@ReadOnlyComposable
internal fun localizedMessage(distance: CoarseDistance): String = LocalCoarseDistanceFormatter.current.format(distance)
