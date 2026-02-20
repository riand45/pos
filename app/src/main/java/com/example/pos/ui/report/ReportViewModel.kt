package com.example.pos.ui.report

import android.app.Application
import androidx.lifecycle.*
import com.example.pos.PosApplication
import com.example.pos.data.entity.Product
import com.example.pos.data.entity.ProductSalesReport
import com.example.pos.data.entity.Transaction
import com.example.pos.utils.DateFormatter
import io.github.jan.supabase.gotrue.auth

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val transactionRepository = posApp.transactionRepository
    private val orderRepository = posApp.orderRepository
    private val productRepository = posApp.productRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    private val _startDate = MutableLiveData<Long>(DateFormatter.getStartOfDay(System.currentTimeMillis()))
    val startDate: LiveData<Long> = _startDate

    private val _endDate = MutableLiveData<Long>(DateFormatter.getEndOfDay(System.currentTimeMillis()))
    val endDate: LiveData<Long> = _endDate

    private val filterTrigger = MediatorLiveData<Unit>().apply {
        addSource(_startDate) { value = Unit }
        addSource(_endDate) { value = Unit }
        addSource(posApp.currentUserId) { value = Unit }
    }

    val totalRevenue: LiveData<Double> = filterTrigger.switchMap {
        val uid = posApp.currentUserId.value ?: userId
        transactionRepository.getTotalRevenueRange(uid, _startDate.value!!, _endDate.value!!)
    }

    val totalProfit: LiveData<Double> = filterTrigger.switchMap {
        val uid = posApp.currentUserId.value ?: userId
        transactionRepository.getTotalProfitRange(uid, _startDate.value!!, _endDate.value!!)
    }

    val topProducts: LiveData<List<ProductSalesReport>> = filterTrigger.switchMap {
        val uid = posApp.currentUserId.value ?: userId
        val start = _startDate.value ?: DateFormatter.getStartOfDay(System.currentTimeMillis())
        val end = _endDate.value ?: DateFormatter.getEndOfDay(System.currentTimeMillis())
        orderRepository.getProductsSoldByDateRange(uid, start, end, 5)
    }

    val lowStockProducts: LiveData<List<Product>> = productRepository.getLowStockProducts()

    val transactions: LiveData<List<Transaction>> = filterTrigger.switchMap {
        val uid = posApp.currentUserId.value ?: userId
        transactionRepository.getTransactionsByRange(uid, _startDate.value!!, _endDate.value!!)
    }

    fun setDateRange(start: Long, end: Long) {
        _startDate.value = DateFormatter.getStartOfDay(start, sourceIsUtc = true)
        _endDate.value = DateFormatter.getEndOfDay(end, sourceIsUtc = true)
    }
}
