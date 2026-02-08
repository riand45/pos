package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.TransactionDao
import com.example.pos.data.entity.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    fun getAllTransactions(userId: String): LiveData<List<Transaction>> =
            transactionDao.getAllTransactions(userId)
    fun getTodayTransactions(userId: String): LiveData<List<Transaction>> =
            transactionDao.getTodayTransactions(userId)
    fun getTodayTotalRevenue(userId: String): LiveData<Double> =
            transactionDao.getTodayTotalRevenue(userId)
    fun getTodayTransactionCount(userId: String): LiveData<Int> =
            transactionDao.getTodayTransactionCount(userId)

    fun getTransactionsByDate(userId: String, dateMillis: Long): LiveData<List<Transaction>> =
            transactionDao.getTransactionsByDate(userId, dateMillis)

    fun getTransactionsByDateRange(
            userId: String,
            startDate: Long,
            endDate: Long
    ): LiveData<List<Transaction>> =
            transactionDao.getTransactionsByDateRange(userId, startDate, endDate)

    fun getTotalRevenueByDate(userId: String, dateMillis: Long): LiveData<Double> =
            transactionDao.getTotalRevenueByDate(userId, dateMillis)

    suspend fun insert(transaction: Transaction): Long = transactionDao.insert(transaction)

    suspend fun update(transaction: Transaction) = transactionDao.update(transaction)

    suspend fun delete(transaction: Transaction) = transactionDao.delete(transaction)

    suspend fun getTransactionById(id: Long): Transaction? = transactionDao.getTransactionById(id)
}
