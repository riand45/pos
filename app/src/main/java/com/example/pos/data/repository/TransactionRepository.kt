package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.TransactionDao
import com.example.pos.data.entity.Transaction

class TransactionRepository(val transactionDao: TransactionDao) {
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

    suspend fun getTransactionsWithItemsByDateRange(
            userId: String,
            startDate: Long,
            endDate: Long
    ): List<com.example.pos.data.entity.TransactionWithItems> =
            transactionDao.getTransactionsWithItemsByDateRange(userId, startDate, endDate)

    fun getTotalRevenueByDate(userId: String, dateMillis: Long): LiveData<Double> =
            transactionDao.getTotalRevenueByDate(userId, dateMillis)

    fun getTotalRevenueRange(userId: String, startDate: Long, endDate: Long): LiveData<Double> =
            transactionDao.getTotalRevenueRange(userId, startDate, endDate)

    fun getTotalCogsRange(userId: String, startDate: Long, endDate: Long): LiveData<Double> =
            transactionDao.getTotalCogsRange(userId, startDate, endDate)

    fun getTotalProfitRange(userId: String, startDate: Long, endDate: Long): LiveData<Double> =
            transactionDao.getTotalProfitRange(userId, startDate, endDate)

    fun getTransactionsByRange(userId: String, startDate: Long, endDate: Long): LiveData<List<Transaction>> =
            transactionDao.getTransactionsByRange(userId, startDate, endDate)

    suspend fun insert(transaction: Transaction): Long = transactionDao.insert(transaction)

    suspend fun update(transaction: Transaction) = transactionDao.update(transaction)

    suspend fun delete(transaction: Transaction) = transactionDao.delete(transaction)

    suspend fun getTransactionById(id: Long): Transaction? = transactionDao.getTransactionById(id)
}
