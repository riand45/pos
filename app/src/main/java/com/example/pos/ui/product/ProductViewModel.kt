package com.example.pos.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pos.PosApplication
import com.example.pos.data.entity.Product
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val productRepository = posApp.productRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    fun getAllProducts(): LiveData<List<Product>> =
            posApp.currentUserId.switchMap { uid ->
                val activeUid = if (uid.isNotEmpty()) uid else userId
                productRepository.getAllProducts(activeUid)
            }

    fun getProductsByCategory(categoryId: Long): LiveData<List<Product>> =
            posApp.currentUserId.switchMap { uid ->
                val activeUid = if (uid.isNotEmpty()) uid else userId
                productRepository.getProductsByCategory(categoryId, activeUid)
            }

    fun insert(
            categoryId: Long,
            name: String,
            price: Double,
            cogs: Double = 0.0,
            stock: Int?,
            imagePath: String? = null
    ) {
        viewModelScope.launch {
            productRepository.insert(
                    Product(
                            categoryId = categoryId,
                            name = name,
                            price = price,
                            cogs = cogs,
                            stock = stock,
                            imagePath = imagePath,
                            userId = userId
                    )
            )
        }
    }

    fun update(
            product: Product,
            categoryId: Long,
            name: String,
            price: Double,
            cogs: Double = 0.0,
            stock: Int?,
            imagePath: String? = null
    ) {
        viewModelScope.launch {
            productRepository.update(
                    product.copy(
                            categoryId = categoryId,
                            name = name,
                            price = price,
                            cogs = cogs,
                            stock = stock,
                            imagePath = imagePath ?: product.imagePath,
                            userId = userId
                    )
            )
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch { productRepository.delete(product) }
    }
}
