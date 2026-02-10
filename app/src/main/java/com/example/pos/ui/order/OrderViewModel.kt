package com.example.pos.ui.order

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.pos.PosApplication
import com.example.pos.data.entity.*
import com.example.pos.utils.OrderNumberGenerator
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val orderRepository = posApp.orderRepository
    private val productRepository = posApp.productRepository
    private val transactionRepository = posApp.transactionRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _isNewestFirst = MutableLiveData(true)
    val isNewestFirst: LiveData<Boolean> = _isNewestFirst

    private val _viewMode = MutableLiveData(ViewMode.KANBAN)
    val viewMode: LiveData<ViewMode> = _viewMode

    enum class ViewMode { KANBAN, LIST }

    private val _filterTrigger = MediatorLiveData<Unit>().apply {
        addSource(_searchQuery) { value = Unit }
        addSource(_isNewestFirst) { value = Unit }
        addSource(posApp.currentUserId) { value = Unit }
    }

    val queueOrders = _filterTrigger.switchMap {
        val query = _searchQuery.value ?: ""
        val isNewest = _isNewestFirst.value ?: true
        val uid = posApp.currentUserId.value ?: userId
        orderRepository.getOrderWithItemsByStatus(OrderStatus.QUEUE, uid, query, isNewest)
    }

    val processOrders = _filterTrigger.switchMap {
        val query = _searchQuery.value ?: ""
        val isNewest = _isNewestFirst.value ?: true
        val uid = posApp.currentUserId.value ?: userId
        orderRepository.getOrderWithItemsByStatus(OrderStatus.PROCESS, uid, query, isNewest)
    }

    val doneOrders = _filterTrigger.switchMap {
        val query = _searchQuery.value ?: ""
        val isNewest = _isNewestFirst.value ?: true
        val uid = posApp.currentUserId.value ?: userId
        orderRepository.getTodayOrderWithItemsByStatus(OrderStatus.DONE, uid, query, isNewest)
    }

    val allOrders = _filterTrigger.switchMap {
        val query = _searchQuery.value ?: ""
        val isNewest = _isNewestFirst.value ?: true
        val uid = posApp.currentUserId.value ?: userId
        orderRepository.getAllOrdersWithItems(uid, query, isNewest)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSortOrder() {
        _isNewestFirst.value = !(_isNewestFirst.value ?: true)
    }

    fun setViewMode(mode: ViewMode) {
        _viewMode.value = mode
    }

    fun getAllProducts(): LiveData<List<Product>> = productRepository.getAllProducts(userId)

    private val _selectedOrder = MutableLiveData<Order?>()
    val selectedOrder: LiveData<Order?> = _selectedOrder

    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems

    // Cart for new order
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> = _cartItems

    fun updateOrderStatus(order: Order, newStatus: OrderStatus) {
        viewModelScope.launch { orderRepository.updateStatus(order.id, newStatus) }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch { orderRepository.delete(order) }
    }

    fun archiveOrder(order: Order) {
        viewModelScope.launch { orderRepository.updateStatus(order.id, OrderStatus.ARCHIVED) }
    }

    fun selectOrder(order: Order) {
        _selectedOrder.value = order
        viewModelScope.launch {
            val items = orderRepository.getItemsByOrderList(order.id)
            _orderItems.postValue(items)
        }
    }

    fun clearSelection() {
        _selectedOrder.value = null
        _orderItems.value = emptyList()
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val currentCart = _cartItems.value ?: mutableListOf()
        val existingItem = currentCart.find { it.productId == product.id }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            currentCart.add(
                    CartItem(
                            productId = product.id,
                            productName = product.name,
                            unitPrice = product.price,
                            quantity = quantity
                    )
            )
        }
        _cartItems.value = currentCart
    }

    fun removeFromCart(productId: Long) {
        val currentCart = _cartItems.value ?: mutableListOf()
        currentCart.removeAll { it.productId == productId }
        _cartItems.value = currentCart
    }

    fun clearCart() {
        _cartItems.value = mutableListOf()
    }

    fun createOrder(tableInfo: String? = null) {
        viewModelScope.launch {
            val cart = _cartItems.value ?: return@launch
            if (cart.isEmpty()) return@launch

            val orderCount = orderRepository.getTodayOrderCount()
            val orderNumber = OrderNumberGenerator.generateShort(orderCount)

            val totalItems = cart.sumOf { it.quantity }
            val totalPrice = cart.sumOf { it.totalPrice }

            val order =
                    Order(
                            orderNumber = orderNumber,
                            status = OrderStatus.QUEUE,
                            totalItems = totalItems,
                            totalPrice = totalPrice,
                            tableInfo = tableInfo,
                            userId = userId
                    )

            val orderId = orderRepository.insert(order)

            val orderItems =
                    cart.map { cartItem ->
                        OrderItem(
                                orderId = orderId,
                                productId = cartItem.productId,
                                productName = cartItem.productName,
                                quantity = cartItem.quantity,
                                unitPrice = cartItem.unitPrice,
                                totalPrice = cartItem.totalPrice,
                                userId = userId
                        )
                    }

            orderRepository.insertOrderItems(orderItems)
            clearCart()
        }
    }

    fun completeTransaction(
            order: Order,
            paymentMethod: PaymentMethod,
            amountPaid: Double,
            bankName: String? = null,
            customer: Customer? = null
    ) {
        viewModelScope.launch {
            val subtotal = order.totalPrice
            val tax = 0.0
            val totalAmount = subtotal
            val change = if (paymentMethod == PaymentMethod.CASH) amountPaid - totalAmount else 0.0

            val transaction =
                    Transaction(
                            orderId = order.id,
                            orderNumber = order.orderNumber,
                            paymentMethod = paymentMethod,
                            subtotal = subtotal,
                            tax = tax,
                            totalAmount = totalAmount,
                            amountPaid = amountPaid,
                            changeAmount = change,
                            bankName = bankName,
                            customerId = customer?.id ?: order.customerId,
                            customerName = customer?.name ?: order.customerName,
                            userId = userId
                    )

            transactionRepository.insert(transaction)
            
            // Decrease stock for sold items
            val orderItems = orderRepository.getItemsByOrderList(order.id)
            orderItems.forEach { item ->
                productRepository.decreaseStock(item.productId, item.quantity)
            }
            
            // Update order status and customer info if provided
            val updatedOrder = order.copy(
                status = OrderStatus.DONE, 
                customerId = customer?.id ?: order.customerId,
                customerName = customer?.name ?: order.customerName
            )
            orderRepository.update(updatedOrder)
            
            clearSelection()
        }
    }

    data class CartItem(
            val productId: Long,
            val productName: String,
            val unitPrice: Double,
            var quantity: Int
    ) {
        val totalPrice: Double
            get() = unitPrice * quantity
    }
}
