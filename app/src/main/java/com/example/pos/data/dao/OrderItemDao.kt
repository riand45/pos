package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.OrderItem

@Dao
interface OrderItemDao {
    @Query("SELECT * FROM order_items WHERE user_id = :userId")
    suspend fun getAllOrderItemsList(userId: String): List<OrderItem>
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getItemsByOrder(orderId: Long): LiveData<List<OrderItem>>

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getItemsByOrderList(orderId: Long): List<OrderItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(orderItem: OrderItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orderItems: List<OrderItem>)

    @Update suspend fun update(orderItem: OrderItem)

    @Delete suspend fun delete(orderItem: OrderItem)

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteByOrderId(orderId: Long)
}
