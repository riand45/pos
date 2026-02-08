package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.ProductDao
import com.example.pos.data.entity.Product

class ProductRepository(val productDao: ProductDao) {
    fun getAllProducts(userId: String): LiveData<List<Product>> = productDao.getAllProducts(userId)

    fun getProductsByCategory(categoryId: Long, userId: String): LiveData<List<Product>> =
            productDao.getProductsByCategory(categoryId, userId)

    fun getLowStockProducts(): LiveData<List<Product>> = productDao.getLowStockProducts()

    suspend fun insert(product: Product): Long = productDao.insert(product)

    suspend fun update(product: Product) = productDao.update(product)

    suspend fun delete(product: Product) = productDao.delete(product)

    suspend fun deleteById(id: Long) = productDao.deleteById(id)

    suspend fun getProductById(id: Long): Product? = productDao.getProductById(id)

    suspend fun getAllProductsList(userId: String): List<Product> =
            productDao.getAllProductsList(userId)

    suspend fun decreaseStock(productId: Long, quantity: Int) =
            productDao.decreaseStock(productId, quantity)
}
