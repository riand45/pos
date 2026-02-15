package com.example.pos.ui.dashboard

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.map
import com.example.pos.PosApplication
import com.example.pos.data.entity.Transaction
import com.example.pos.utils.DateFormatter
import io.github.jan.supabase.gotrue.auth
import java.util.*

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val transactionRepository = posApp.transactionRepository
    private val productRepository = posApp.productRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    fun getTodayStats(): LiveData<List<Transaction>> =
            posApp.currentUserId.switchMap { uid ->
                val activeUid = if (uid.isNotEmpty()) uid else userId
                transactionRepository.getTransactionsByDateRange(
                        activeUid,
                        DateFormatter.getStartOfDay(System.currentTimeMillis()),
                        DateFormatter.getEndOfDay(System.currentTimeMillis())
                )
            }

    fun getActiveProductsCount(): LiveData<Int> =
            posApp.currentUserId.switchMap { uid ->
                val activeUid = if (uid.isNotEmpty()) uid else userId
                productRepository.getAllProducts(activeUid).map { it.size }
            }

    // Get revenue for last 7 days for the chart
    fun getWeeklyRevenue(): LiveData<Map<String, Double>> {
        return posApp.currentUserId.switchMap { uid ->
            val activeUid = if (uid.isNotEmpty()) uid else userId
            val calendar = Calendar.getInstance()
            val end = DateFormatter.getEndOfDay(calendar.timeInMillis)
            calendar.add(Calendar.DAY_OF_YEAR, -6)
            val start = DateFormatter.getStartOfDay(calendar.timeInMillis)

            transactionRepository.getTransactionsByDateRange(activeUid, start, end).map { transactions ->
                val dailyMap = mutableMapOf<String, Double>()

                // Initialize last 7 days with 0
                val tempCal = Calendar.getInstance()
                tempCal.timeInMillis = start
                for (i in 0..6) {
                    val dayKey = DateFormatter.formatDateShort(tempCal.timeInMillis)
                    dailyMap[dayKey] = 0.0
                    tempCal.add(Calendar.DAY_OF_YEAR, 1)
                }

                // Fill with real data
                transactions.forEach { tx ->
                    val dayKey = DateFormatter.formatDateShort(tx.createdAt)
                    if (dailyMap.containsKey(dayKey)) {
                        dailyMap[dayKey] = dailyMap[dayKey]!! + tx.totalAmount
                    }
                }
                dailyMap
            }
        }
    }
}
