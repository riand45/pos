package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.Order
import com.example.pos.data.entity.OrderStatus
import com.example.pos.data.entity.OrderWithItems

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE user_id = :userId ORDER BY createdAt DESC")
    fun getAllOrders(userId: String): LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE user_id = :userId ORDER BY createdAt DESC")
    suspend fun getAllOrdersList(userId: String): List<Order>

    @Query(
            "SELECT * FROM orders WHERE status = :status AND user_id = :userId ORDER BY createdAt DESC"
    )
    fun getOrdersByStatus(status: OrderStatus, userId: String): LiveData<List<Order>>

    @Transaction
    @Query(
            "SELECT * FROM orders WHERE status = :status AND user_id = :userId ORDER BY createdAt DESC"
    )
    fun getOrderWithItemsByStatus(
            status: OrderStatus,
            userId: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE status = :status AND user_id = :userId " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt DESC"
    )
    fun getOrderWithItemsByStatusAndSearch(
        status: OrderStatus,
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE status = :status AND user_id = :userId " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt ASC"
    )
    fun getOrderWithItemsByStatusAndSearchAsc(
        status: OrderStatus,
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE status = :status AND user_id = :userId AND " +
                "date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime') " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt DESC"
    )
    fun getTodayOrderWithItemsByStatusAndSearch(
        status: OrderStatus,
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE status = :status AND user_id = :userId AND " +
                "date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime') " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt ASC"
    )
    fun getTodayOrderWithItemsByStatusAndSearchAsc(
        status: OrderStatus,
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE user_id = :userId " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt DESC"
    )
    fun getAllOrdersWithItemsAndSearch(
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Transaction
    @Query(
        "SELECT * FROM orders WHERE user_id = :userId " +
                "AND (orderNumber LIKE '%' || :query || '%' OR customerName LIKE '%' || :query || '%') " +
                "ORDER BY createdAt ASC"
    )
    fun getAllOrdersWithItemsAndSearchAsc(
        userId: String,
        query: String
    ): LiveData<List<OrderWithItems>>

    @Query(
            "SELECT * FROM orders WHERE status = :status AND user_id = :userId ORDER BY createdAt DESC"
    )
    suspend fun getOrdersByStatusList(status: OrderStatus, userId: String): List<Order>

    @Query("SELECT * FROM orders WHERE id = :id") suspend fun getOrderById(id: Long): Order?

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :id")
    fun getOrderWithItemsById(id: Long): LiveData<OrderWithItems?>

    @Query("SELECT * FROM orders WHERE id = :id") fun getOrderByIdLive(id: Long): LiveData<Order?>

    @Query(
            "SELECT COUNT(*) FROM orders WHERE date(createdAt/1000, 'unixepoch', 'localtime') = date('now', 'localtime')"
    )
    suspend fun getTodayOrderCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(order: Order): Long

    @Update suspend fun update(order: Order)

    @Query("UPDATE orders SET status = :status, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updateStatus(
            orderId: Long,
            status: OrderStatus,
            updatedAt: Long = System.currentTimeMillis()
    )

    @Delete suspend fun delete(order: Order)

    @Query("DELETE FROM orders WHERE id = :id") suspend fun deleteById(id: Long)
}
