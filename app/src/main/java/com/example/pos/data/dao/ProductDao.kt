package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE user_id = :userId ORDER BY createdAt DESC")
    fun getAllProducts(userId: String): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE user_id = :userId ORDER BY createdAt DESC")
    suspend fun getAllProductsList(userId: String): List<Product>

    @Query(
            "SELECT * FROM products WHERE categoryId = :categoryId AND user_id = :userId ORDER BY createdAt DESC"
    )
    fun getProductsByCategory(categoryId: Long, userId: String): LiveData<List<Product>>

    @Query(
            "SELECT * FROM products WHERE categoryId = :categoryId AND user_id = :userId ORDER BY createdAt DESC"
    )
    suspend fun getProductsByCategoryList(categoryId: Long, userId: String): List<Product>

    @Query("SELECT * FROM products WHERE id = :id") suspend fun getProductById(id: Long): Product?

    @Query("SELECT * FROM products WHERE stock IS NOT NULL AND stock <= 5")
    fun getLowStockProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(product: Product): Long

    @Update suspend fun update(product: Product)

    @Delete suspend fun delete(product: Product)

    @Query("DELETE FROM products WHERE id = :id") suspend fun deleteById(id: Long)

    @Query(
            "UPDATE products SET stock = stock - :quantity WHERE id = :productId AND stock >= :quantity"
    )
    suspend fun decreaseStock(productId: Long, quantity: Int)
}
