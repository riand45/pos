package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.ExpenseDao
import com.example.pos.data.entity.Expense

class ExpenseRepository(val expenseDao: ExpenseDao) {
    fun getAllExpenses(userId: String): LiveData<List<Expense>> = expenseDao.getAllExpenses(userId)

    fun getExpensesByDateRange(userId: String, start: Long, end: Long): LiveData<List<Expense>> {
        return expenseDao.getExpensesByDateRange(userId, start, end)
    }

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}
