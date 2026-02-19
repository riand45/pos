package com.example.pos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pos.data.dao.*
import com.example.pos.data.entity.*
import androidx.room.migration.Migration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
        entities =
                [
                        Category::class,
                        Product::class,
                        Order::class,
                        OrderItem::class,
                        Transaction::class,
                        Expense::class,
                        Customer::class],
        version = 7,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
        abstract fun categoryDao(): CategoryDao
        abstract fun productDao(): ProductDao
        abstract fun orderDao(): OrderDao
        abstract fun orderItemDao(): OrderItemDao
        abstract fun transactionDao(): TransactionDao
        abstract fun expenseDao(): ExpenseDao
        abstract fun customerDao(): CustomerDao
 
        companion object {
                @Volatile private var INSTANCE: AppDatabase? = null
 
                val MIGRATION_6_7 = object : Migration(6, 7) {
                        override fun migrate(db: SupportSQLiteDatabase) {
                                // Add netIncome to order_items
                                db.execSQL("ALTER TABLE order_items ADD COLUMN netIncome REAL NOT NULL DEFAULT 0.0")
                                
                                // Add netIncomeTotal to transactions
                                db.execSQL("ALTER TABLE transactions ADD COLUMN netIncomeTotal REAL NOT NULL DEFAULT 0.0")
                                
                                // Update existing netIncome for order_items: (unitPrice - cogs) * quantity
                                db.execSQL("UPDATE order_items SET netIncome = (unitPrice - cogs) * quantity")
                                
                                // Update existing netIncomeTotal for transactions: totalAmount - totalCogs
                                db.execSQL("UPDATE transactions SET netIncomeTotal = totalAmount - totalCogs")
                        }
                }

                fun getDatabase(context: Context): AppDatabase {
                        return INSTANCE
                                ?: synchronized(this) {
                                        val instance =
                                                Room.databaseBuilder(
                                                                context.applicationContext,
                                                                AppDatabase::class.java,
                                                                "pos_database"
                                                        )
                                                        .addMigrations(MIGRATION_6_7)
                                                        .fallbackToDestructiveMigration()
                                                        .addCallback(DatabaseCallback())
                                                        .build()
                                        INSTANCE = instance
                                        instance
                                }
                }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch { populateDatabase(database) }
                        }
                }

                suspend fun populateDatabase(database: AppDatabase) {
                        // Insert sample categories
                        val categoryDao = database.categoryDao()
                        val categories =
                                listOf(
                                        Category(
                                                id = 1,
                                                name = "Makanan",
                                                description = "Menu makanan",
                                                iconName = "restaurant"
                                        ),
                                        Category(
                                                id = 2,
                                                name = "Minuman",
                                                description = "Menu minuman",
                                                iconName = "local_cafe"
                                        ),
                                        Category(
                                                id = 3,
                                                name = "Snack",
                                                description = "Cemilan",
                                                iconName = "cookie"
                                        ),
                                        Category(
                                                id = 4,
                                                name = "Dessert",
                                                description = "Menu penutup",
                                                iconName = "cake"
                                        )
                                )
                        categories.forEach { categoryDao.insert(it) }

                        // Insert sample products
                        val productDao = database.productDao()
                        val products =
                                listOf(
                                        Product(
                                                id = 1,
                                                categoryId = 1,
                                                name = "Double Cheeseburger XL",
                                                price = 45000.0,
                                                stock = 50
                                        ),
                                        Product(
                                                id = 2,
                                                categoryId = 1,
                                                name = "BBQ Ribs Family Pack",
                                                price = 125000.0,
                                                stock = 20
                                        ),
                                        Product(
                                                id = 3,
                                                categoryId = 1,
                                                name = "Margherita Pizza Large",
                                                price = 85000.0,
                                                stock = 30
                                        ),
                                        Product(
                                                id = 4,
                                                categoryId = 2,
                                                name = "Cappuccino Latte x2",
                                                price = 22000.0,
                                                stock = 100
                                        ),
                                        Product(
                                                id = 5,
                                                categoryId = 2,
                                                name = "Kopi Susu Gula Aren",
                                                price = 20000.0,
                                                stock = 100
                                        ),
                                        Product(
                                                id = 6,
                                                categoryId = 3,
                                                name = "Seasonal Fruit Salad",
                                                price = 35000.0,
                                                stock = 25
                                        ),
                                        Product(
                                                id = 7,
                                                categoryId = 4,
                                                name = "Pancake Tower Special",
                                                price = 78000.0,
                                                stock = 15
                                        ),
                                        Product(
                                                id = 8,
                                                categoryId = 2,
                                                name = "Mineral Water",
                                                price = 5000.0,
                                                stock = 200
                                        ),
                                        Product(
                                                id = 9,
                                                categoryId = 1,
                                                name = "Butter Croissant",
                                                price = 25000.0,
                                                stock = 40
                                        ),
                                        Product(
                                                id = 10,
                                                categoryId = 2,
                                                name = "Caramel Latte Extra Shot",
                                                price = 32000.0,
                                                stock = 80
                                        )
                                )
                        products.forEach { productDao.insert(it) }
                }
        }
}
