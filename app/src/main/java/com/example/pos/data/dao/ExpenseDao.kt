package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE user_id = :userId ORDER BY date DESC")
    fun getAllExpenses(userId: String): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE user_id = :userId ORDER BY date DESC")
    suspend fun getAllExpensesList(userId: String): List<Expense>

    @Query(
            "SELECT * FROM expenses WHERE user_id = :userId AND date BETWEEN :start AND :end ORDER BY date DESC"
    )
    fun getExpensesByDateRange(userId: String, start: Long, end: Long): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(expense: Expense)

    @Update suspend fun update(expense: Expense)

    @Delete suspend fun delete(expense: Expense)

    @Query("DELETE FROM expenses WHERE id = :id") suspend fun deleteById(id: Long)
}
