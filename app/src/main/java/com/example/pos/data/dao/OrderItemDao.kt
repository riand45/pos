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


    @Query("SELECT productId, productName, SUM(quantity) as totalQuantity, SUM(totalPrice) as totalSales " +
           "FROM order_items WHERE user_id = :userId GROUP BY productId ORDER BY totalQuantity DESC LIMIT :limit")
    fun getTopSellingProducts(userId: String, limit: Int): LiveData<List<com.example.pos.data.entity.ProductSalesReport>>

    @Query("SELECT oi.productId, oi.productName, SUM(oi.quantity) as totalQuantity, SUM(oi.totalPrice) as totalSales " +
           "FROM order_items oi " +
           "INNER JOIN orders o ON oi.orderId = o.id " +
           "WHERE o.user_id = :userId " +
           "AND o.status IN ('DONE', 'ARCHIVED') " +
           "AND o.createdAt BETWEEN :startDate AND :endDate " +
           "GROUP BY oi.productId " +
           "ORDER BY totalQuantity DESC " +
           "LIMIT :limit")
    fun getProductsSoldByDateRange(userId: String, startDate: Long, endDate: Long, limit: Int): LiveData<List<com.example.pos.data.entity.ProductSalesReport>>

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteByOrderId(orderId: Long)
}
