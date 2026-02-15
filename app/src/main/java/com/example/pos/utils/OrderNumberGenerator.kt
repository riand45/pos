package com.example.pos.utils

import java.text.SimpleDateFormat
import java.util.*

object OrderNumberGenerator {
    private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

    fun generate(orderCount: Int): String {
        val date = dateFormat.format(Date())
        val sequence = String.format("%03d", orderCount + 1)
        return "#ORD-$date-$sequence"
    }

    fun generateShort(orderCount: Int): String {
        return "#ORD-${1000 + orderCount + 1}"
    }
}
