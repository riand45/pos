package com.example.pos.ui.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.pos.PosApplication
import com.example.pos.data.entity.Expense
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val expenseRepository = posApp.expenseRepository

    private val _startDate = MutableLiveData<Long?>(null)
    private val _endDate = MutableLiveData<Long?>(null)
    private val _categoryFilter = MutableLiveData<String?>(null)

    val startDate: LiveData<Long?> = _startDate
    val endDate: LiveData<Long?> = _endDate

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    val filteredExpenses: LiveData<List<Expense>> = MediatorLiveData<List<Expense>>().apply {
        var allExpenses: List<Expense> = emptyList()
        
        fun update() {
            val start = _startDate.value
            val end = _endDate.value
            val category = _categoryFilter.value

            value = allExpenses.filter { expense ->
                val dateMatch = if (start != null && end != null) {
                    expense.date in start..end
                } else true
                
                val categoryMatch = if (!category.isNullOrEmpty() && category != "All") {
                    expense.category == category
                } else true
                
                dateMatch && categoryMatch
            }
        }

        addSource(expenseRepository.getAllExpenses(userId)) { 
            allExpenses = it
            update()
        }
        addSource(_startDate) { update() }
        addSource(_endDate) { update() }
        addSource(_categoryFilter) { update() }
    }

    fun setDateRange(start: Long?, end: Long?) {
        _startDate.value = start
        _endDate.value = end
    }

    fun setCategoryFilter(category: String?) {
        _categoryFilter.value = category
    }

    fun getAllExpenses(): LiveData<List<Expense>> = expenseRepository.getAllExpenses(userId)

    fun insert(expense: Expense) {
        viewModelScope.launch { expenseRepository.insert(expense.copy(userId = userId)) }
    }

    fun update(expense: Expense) {
        viewModelScope.launch { expenseRepository.update(expense.copy(userId = userId)) }
    }

    fun delete(expense: Expense) {
        viewModelScope.launch { expenseRepository.delete(expense) }
    }
}
