package com.example.pos.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    private val fullFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    private val relativeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val shortDateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    fun formatTime(timestamp: Long): String = timeFormat.format(Date(timestamp))

    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun formatFull(timestamp: Long): String = fullFormat.format(Date(timestamp))

    fun formatDateShort(timestamp: Long): String = shortDateFormat.format(Date(timestamp))

    fun formatShortDate(timestamp: Long): String = shortDateFormat.format(Date(timestamp))

    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60_000 -> "Baru saja"
            diff < 3_600_000 -> "${diff / 60_000}m yang lalu"
            diff < 86_400_000 -> "${diff / 3_600_000}h yang lalu"
            else -> formatDate(timestamp)
        }
    }

    fun isToday(timestamp: Long): Boolean {
        val today = Calendar.getInstance()
        val date = Calendar.getInstance().apply { timeInMillis = timestamp }
        return today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) &&
               today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
    }

    fun formatLongDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun getStartOfDay(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    fun getEndOfDay(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }
}
