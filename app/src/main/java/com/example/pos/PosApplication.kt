package com.example.pos

import android.app.Application
import com.example.pos.data.AppDatabase
import com.example.pos.data.local.SharedPreferencesSessionManager
import com.example.pos.data.repository.CategoryRepository
import com.example.pos.data.repository.ExpenseRepository
import com.example.pos.data.repository.OrderRepository
import com.example.pos.data.repository.ProductRepository
import com.example.pos.data.repository.TransactionRepository
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest

class PosApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    val categoryRepository by lazy { CategoryRepository(database.categoryDao()) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    val orderRepository by lazy { OrderRepository(database.orderDao(), database.orderItemDao()) }
    val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    val expenseRepository by lazy { ExpenseRepository(database.expenseDao()) }
    val customerRepository by lazy { com.example.pos.data.repository.CustomerRepository(database.customerDao()) }

    val supabase by lazy {
        createSupabaseClient(
                supabaseUrl = "https://ashapfjcxwfasccqslxy.supabase.co",
                supabaseKey = "sb_publishable_OnJGeDUxV6G_Y75FKIQNaw_s0HCoIFl"
        ) {
            install(Postgrest)
            install(Auth) {
                sessionManager = SharedPreferencesSessionManager(this@PosApplication)
                autoLoadFromStorage = true
                alwaysAutoRefresh = true
            }
        }
    }
}
