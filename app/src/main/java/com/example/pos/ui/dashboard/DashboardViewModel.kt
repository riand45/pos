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
            transactionRepository.getTransactionsByDateRange(
                    userId,
                    DateFormatter.getStartOfDay(System.currentTimeMillis()),
                    DateFormatter.getEndOfDay(System.currentTimeMillis())
            )

    fun getActiveProductsCount(): LiveData<Int> =
            productRepository.getAllProducts(userId).map { it.size }

    // Get revenue for last 7 days for the chart
    fun getWeeklyRevenue(): LiveData<Map<String, Double>> {
        val result = MutableLiveData<Map<String, Double>>()
        val calendar = Calendar.getInstance()
        val end = DateFormatter.getEndOfDay(calendar.timeInMillis)

        calendar.add(Calendar.DAY_OF_YEAR, -6)
        val start = DateFormatter.getStartOfDay(calendar.timeInMillis)

        // This is a simplified implementation. In a real app,
        // you'd use a custom DAO query for aggregation.
        transactionRepository.getTransactionsByDateRange(userId, start, end).observeForever {
                transactions ->
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
            result.postValue(dailyMap)
        }
        return result
    }
}
