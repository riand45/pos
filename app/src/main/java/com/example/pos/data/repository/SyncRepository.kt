package com.example.pos.data.repository

import com.example.pos.data.dao.*
import com.example.pos.data.entity.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class SyncRepository(
        private val categoryDao: CategoryDao,
        private val productDao: ProductDao,
        private val orderDao: OrderDao,
        private val orderItemDao: OrderItemDao,
        private val expenseDao: ExpenseDao,
        private val customerDao: CustomerDao,
        private val transactionDao: TransactionDao,
        private val supabase: SupabaseClient
) {

    suspend fun backupData(userId: String) {
        val categories = categoryDao.getAllCategoriesList(userId)
        if (categories.isNotEmpty()) {
            supabase.postgrest.from("categories").upsert(categories) { select(Columns.ALL) }
        }

        val customers = customerDao.getAllCustomersList(userId)
        if (customers.isNotEmpty()) {
            supabase.postgrest.from("customers").upsert(customers) { select(Columns.ALL) }
        }

        val products = productDao.getAllProductsList(userId)
        if (products.isNotEmpty()) {
            supabase.postgrest.from("products").upsert(products) { select(Columns.ALL) }
        }

        val orders = orderDao.getAllOrdersList(userId)
        if (orders.isNotEmpty()) {
            supabase.postgrest.from("orders").upsert(orders) { select(Columns.ALL) }
        }

        val orderItems = orderItemDao.getAllOrderItemsList(userId)
        if (orderItems.isNotEmpty()) {
            supabase.postgrest.from("order_items").upsert(orderItems) { select(Columns.ALL) }
        }

        val expenses = expenseDao.getAllExpensesList(userId)
        if (expenses.isNotEmpty()) {
            supabase.postgrest.from("expenses").upsert(expenses) { select(Columns.ALL) }
        }

        val transactions = transactionDao.getAllTransactionsList(userId)
        if (transactions.isNotEmpty()) {
            supabase.postgrest.from("transactions").upsert(transactions) { select(Columns.ALL) }
        }
    }

    suspend fun importData(userId: String) {
        val categories =
                supabase.postgrest
                        .from("categories")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Category>()
        val products =
                supabase.postgrest
                        .from("products")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Product>()
        val orders =
                supabase.postgrest
                        .from("orders")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Order>()
        val orderItems =
                supabase.postgrest
                        .from("order_items")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<OrderItem>()
        val expenses =
                supabase.postgrest
                        .from("expenses")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Expense>()
        val customers =
                supabase.postgrest
                        .from("customers")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Customer>()
        val transactions =
                supabase.postgrest
                        .from("transactions")
                        .select(Columns.ALL) { filter { eq("user_id", userId) } }
                        .decodeList<Transaction>()

        // Overwrite local data
        categories.forEach { categoryDao.insert(it) }
        products.forEach { productDao.insert(it) }
        orders.forEach { orderDao.insert(it) }
        orderItems.forEach { orderItemDao.insert(it) }
        expenses.forEach { expenseDao.insert(it) }
        customers.forEach { customerDao.insert(it) }
        transactions.forEach { transactionDao.insert(it) }
    }
}
