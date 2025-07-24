package com.example.coffe1706.core.ui.internationalization.formatter.price

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import java.math.BigDecimal

/**
 * CompositionLocal для проброса форматтера цен
 */
public val LocalPriceFormatter : ProvidableCompositionLocal<PriceFormatter> = staticCompositionLocalOf { error("LocalPriceFormatter not initialized") }

@Composable
public fun rememberLocalPriceFormatter(): PriceFormatter {
    return remember { PriceFormatter() }
}

@Composable
@ReadOnlyComposable
public fun localizedPrice(price: BigDecimal): String = LocalPriceFormatter.current.format(price)
