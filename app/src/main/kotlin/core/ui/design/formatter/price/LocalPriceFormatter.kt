package com.example.coffe1706.core.ui.design.formatter.price

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import java.math.BigDecimal


/**
 * CompositionLocal для проброса форматтера цен
 */
val LocalPriceFormatter : ProvidableCompositionLocal<PriceFormatter> = staticCompositionLocalOf { error("LocalPriceFormatter not initialized") }

@Composable
fun rememberLocalPriceFormatter(): PriceFormatter {
    val resources = LocalContext.current.resources
    return remember { PriceFormatter(resources) }
}

@Composable
@ReadOnlyComposable
internal fun localizedPrice(price: BigDecimal): String = LocalPriceFormatter.current.format(price)
