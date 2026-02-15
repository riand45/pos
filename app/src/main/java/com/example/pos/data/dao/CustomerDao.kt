package com.example.pos.data.dao

import androidx.room.*
import com.example.pos.data.entity.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers WHERE user_id = :userId ORDER BY name ASC")
    fun getAllCustomers(userId: String): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE user_id = :userId")
    suspend fun getAllCustomersList(userId: String): List<Customer>

    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Long): Customer?

    @Query("SELECT * FROM customers WHERE user_id = :userId AND (name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%')")
    fun searchCustomers(userId: String, query: String): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer): Long

    @Update
    suspend fun update(customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("DELETE FROM customers")
    suspend fun deleteAll()
}
