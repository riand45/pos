package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.OrderDao
import com.example.pos.data.dao.OrderItemDao
import com.example.pos.data.entity.Order
import com.example.pos.data.entity.OrderItem
import com.example.pos.data.entity.OrderStatus
import com.example.pos.data.entity.OrderWithItems

class OrderRepository(val orderDao: OrderDao, val orderItemDao: OrderItemDao) {
    fun getAllOrders(userId: String): LiveData<List<Order>> = orderDao.getAllOrders(userId)

    fun getOrdersByStatus(status: OrderStatus, userId: String): LiveData<List<Order>> =
            orderDao.getOrdersByStatus(status, userId)

    fun getOrderWithItemsByStatus(
            status: OrderStatus,
            userId: String,
            query: String,
            isNewestFirst: Boolean
    ): LiveData<List<OrderWithItems>> {
        return if (isNewestFirst) {
            orderDao.getOrderWithItemsByStatusAndSearch(status, userId, query)
        } else {
            orderDao.getOrderWithItemsByStatusAndSearchAsc(status, userId, query)
        }
    }

    fun getTodayOrderWithItemsByStatus(
            status: OrderStatus,
            userId: String,
            query: String,
            isNewestFirst: Boolean
    ): LiveData<List<OrderWithItems>> {
        return if (isNewestFirst) {
            orderDao.getTodayOrderWithItemsByStatusAndSearch(status, userId, query)
        } else {
            orderDao.getTodayOrderWithItemsByStatusAndSearchAsc(status, userId, query)
        }
    }

    fun getAllOrdersWithItems(
        userId: String,
        query: String,
        isNewestFirst: Boolean
    ): LiveData<List<OrderWithItems>> {
        return if (isNewestFirst) {
            orderDao.getAllOrdersWithItemsAndSearch(userId, query)
        } else {
            orderDao.getAllOrdersWithItemsAndSearchAsc(userId, query)
        }
    }

    suspend fun insert(order: Order): Long = orderDao.insert(order)

    suspend fun update(order: Order) = orderDao.update(order)

    suspend fun updateStatus(orderId: Long, status: OrderStatus) =
            orderDao.updateStatus(orderId, status)

    suspend fun delete(order: Order) = orderDao.delete(order)

    suspend fun getOrderById(id: Long): Order? = orderDao.getOrderById(id)

    fun getOrderByIdLive(id: Long): LiveData<Order?> = orderDao.getOrderByIdLive(id)

    fun getOrderWithItemsById(id: Long): LiveData<OrderWithItems?> =
            orderDao.getOrderWithItemsById(id)

    suspend fun getTodayOrderCount(): Int = orderDao.getTodayOrderCount()

    // Order Items
    fun getItemsByOrder(orderId: Long): LiveData<List<OrderItem>> =
            orderItemDao.getItemsByOrder(orderId)

    suspend fun getItemsByOrderList(orderId: Long): List<OrderItem> =
            orderItemDao.getItemsByOrderList(orderId)

    suspend fun insertOrderItem(orderItem: OrderItem): Long = orderItemDao.insert(orderItem)

    suspend fun insertOrderItems(orderItems: List<OrderItem>) = orderItemDao.insertAll(orderItems)

    suspend fun deleteOrderItems(orderId: Long) = orderItemDao.deleteByOrderId(orderId)
}
