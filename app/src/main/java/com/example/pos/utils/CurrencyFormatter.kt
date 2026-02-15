package com.example.pos.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    private val indonesiaLocale = Locale("id", "ID")
    private val currencyFormat = NumberFormat.getCurrencyInstance(indonesiaLocale)

    init {
        currencyFormat.maximumFractionDigits = 0
        currencyFormat.minimumFractionDigits = 0
    }

    fun format(amount: Double): String {
        return currencyFormat.format(amount).replace("Rp", "Rp")
    }

    fun formatShort(amount: Double): String {
        return when {
            amount >= 1_000_000_000 -> "Rp ${String.format(indonesiaLocale, "%.1f", amount / 1_000_000_000)}B"
            amount >= 1_000_000 -> "Rp ${String.format(indonesiaLocale, "%.1f", amount / 1_000_000)}M"
            amount >= 1_000 -> "Rp ${String.format(indonesiaLocale, "%.0f", amount / 1_000)}K"
            else -> format(amount)
        }
    }

    fun formatCompact(amount: Double): String = formatShort(amount)
}
