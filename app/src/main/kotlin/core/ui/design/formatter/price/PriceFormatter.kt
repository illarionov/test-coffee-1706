package com.example.coffe1706.core.ui.design.formatter.price

import android.content.res.Resources
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency

/**
 * Форматтер для цены в виде "200 руб"
 */
class PriceFormatter(
    private val resources: Resources,
) {
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("RUB")
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }

    fun format(price: BigDecimal): String {
        return currencyFormat.format(price)
    }
}
