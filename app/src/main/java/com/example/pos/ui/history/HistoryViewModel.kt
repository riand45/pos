package com.example.pos.ui.history

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.pos.PosApplication
import com.example.pos.data.entity.Transaction
import com.example.pos.utils.DateFormatter
import io.github.jan.supabase.gotrue.auth
import java.util.*
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val transactionRepository = posApp.transactionRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    private val _startDate =
            MutableLiveData<Long>(DateFormatter.getStartOfDay(System.currentTimeMillis()))
    val startDate: LiveData<Long> = _startDate

    private val _endDate =
            MutableLiveData<Long>(DateFormatter.getEndOfDay(System.currentTimeMillis()))
    val endDate: LiveData<Long> = _endDate

    private val _filterStatus = MutableLiveData<String>("All")
    val filterStatus: LiveData<String> = _filterStatus

    private val filterTrigger = MediatorLiveData<Unit>().apply {
        addSource(_startDate) { value = Unit }
        addSource(_endDate) { value = Unit }
        addSource(_filterStatus) { value = Unit }
        addSource(posApp.currentUserId) { value = Unit }
    }

    val transactions: LiveData<List<Transaction>> = filterTrigger.switchMap {
        val start = _startDate.value ?: DateFormatter.getStartOfDay(System.currentTimeMillis())
        val end = _endDate.value ?: DateFormatter.getEndOfDay(System.currentTimeMillis())
        val status = _filterStatus.value ?: "All"
        val uid = posApp.currentUserId.value ?: userId

        transactionRepository.getTransactionsByDateRange(uid, start, end).map { list ->
            when (status) {
                "Success" -> list.filter { it.status == "Success" && !it.isRefunded }
                "Refund" -> list.filter { it.isRefunded }
                else -> list
            }
        }
    }

    val monthlyReport: LiveData<ReportStats> =
            _startDate.switchMap { _ ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                val start = DateFormatter.getStartOfDay(calendar.timeInMillis)
                calendar.set(
                        Calendar.DAY_OF_MONTH,
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )
                val end = DateFormatter.getEndOfDay(calendar.timeInMillis)

                transactionRepository.getTransactionsByDateRange(userId, start, end).map { list ->
                    calculateStats(list)
                }
            }

    val yearlyReport: LiveData<ReportStats> =
            _startDate.switchMap { _ ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                val start = DateFormatter.getStartOfDay(calendar.timeInMillis)
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
                val end = DateFormatter.getEndOfDay(calendar.timeInMillis)

                transactionRepository.getTransactionsByDateRange(userId, start, end).map { list ->
                    calculateStats(list)
                }
            }

    private fun calculateStats(transactions: List<Transaction>): ReportStats {
        val successful = transactions.filter { !it.isRefunded }
        val revenue = successful.sumOf { it.totalAmount }
        val count = successful.size
        val avg = if (count > 0) revenue / count else 0.0
        return ReportStats(revenue, count, avg)
    }

    data class ReportStats(val revenue: Double, val count: Int, val avgBasket: Double)

    fun setDateRange(start: Long, end: Long) {
        _startDate.value = DateFormatter.getStartOfDay(start)
        _endDate.value = DateFormatter.getEndOfDay(end)
    }

    fun setFilter(status: String) {
        _filterStatus.value = status
    }

    fun refundTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.update(transaction.copy(isRefunded = true, status = "Refunded"))
        }
    }
}
