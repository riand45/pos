package com.example.pos.ui.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pos.PosApplication
import com.example.pos.data.entity.Expense
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val expenseRepository = posApp.expenseRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

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
