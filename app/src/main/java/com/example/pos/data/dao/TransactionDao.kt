package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.Transaction

@Dao
interface TransactionDao {
        @Query("SELECT * FROM transactions WHERE user_id = :userId ORDER BY createdAt DESC")
        fun getAllTransactions(userId: String): LiveData<List<Transaction>>

        @Query("SELECT * FROM transactions WHERE user_id = :userId ORDER BY createdAt DESC")
        suspend fun getAllTransactionsList(userId: String): List<Transaction>

        @Query(
                "SELECT * FROM transactions WHERE user_id = :userId AND createdAt BETWEEN :startDate AND :endDate ORDER BY createdAt DESC"
        )
        fun getTransactionsByDateRange(
                userId: String,
                startDate: Long,
                endDate: Long
        ): LiveData<List<Transaction>>

        @Query(
                "SELECT * FROM transactions WHERE user_id = :userId AND date(createdAt/1000, 'unixepoch', 'localtime') = date(:dateMillis/1000, 'unixepoch', 'localtime') ORDER BY createdAt DESC"
        )
        fun getTransactionsByDate(userId: String, dateMillis: Long): LiveData<List<Transaction>>

        @Query(
                "SELECT * FROM transactions WHERE user_id = :userId AND date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime') ORDER BY createdAt DESC"
        )
        fun getTodayTransactions(userId: String): LiveData<List<Transaction>>

        @Query(
                "SELECT COALESCE(SUM(totalAmount), 0) FROM transactions WHERE user_id = :userId AND date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime') AND status = 'Success'"
        )
        fun getTodayTotalRevenue(userId: String): LiveData<Double>

        @Query(
                "SELECT COALESCE(SUM(totalAmount), 0) FROM transactions WHERE user_id = :userId AND date(createdAt/1000, 'unixepoch', 'localtime') = date(:dateMillis/1000, 'unixepoch', 'localtime') AND status = 'Success'"
        )
        fun getTotalRevenueByDate(userId: String, dateMillis: Long): LiveData<Double>

        @Query(
                "SELECT COUNT(*) FROM transactions WHERE user_id = :userId AND date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime')"
        )
        fun getTodayTransactionCount(userId: String): LiveData<Int>

        @Query("SELECT * FROM transactions WHERE id = :id")
        suspend fun getTransactionById(id: Long): Transaction?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(transaction: Transaction): Long

        @Update suspend fun update(transaction: Transaction)

        @Delete suspend fun delete(transaction: Transaction)
}
