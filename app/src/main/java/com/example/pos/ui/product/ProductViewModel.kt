package com.example.pos.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pos.PosApplication
import com.example.pos.data.entity.Product
import io.github.jan.supabase.gotrue.auth
import com.example.pos.data.entity.ProductVariant
import androidx.lifecycle.asLiveData
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
            imagePath: String? = null,
            sku: String? = null,
            discountPrice: Double? = null,
            variants: List<ProductVariant> = emptyList()
    ) {
        viewModelScope.launch {
            val productId = productRepository.insert(
                    Product(
                            categoryId = categoryId,
                            name = name,
                            price = price,
                            cogs = cogs,
                            stock = stock,
                            imagePath = imagePath,
                            sku = sku,
                            discountPrice = discountPrice,
                            userId = userId
                    )
            )
            if (variants.isNotEmpty()) {
                val variantsWithId = variants.map { it.copy(productId = productId, userId = userId) }
                productRepository.insertVariants(variantsWithId)
            }
        }
    }

    fun update(
            product: Product,
            categoryId: Long,
            name: String,
            price: Double,
            cogs: Double = 0.0,
            stock: Int?,
            imagePath: String? = null,
            sku: String? = null,
            discountPrice: Double? = null
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
                            sku = sku ?: product.sku,
                            discountPrice = discountPrice ?: product.discountPrice,
                            userId = userId
                    )
            )
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch { productRepository.delete(product) }
    }

    // Variant management
    fun getVariantsByProduct(productId: Long): LiveData<List<ProductVariant>> =
        productRepository.getVariantsByProduct(productId).asLiveData()

    fun saveVariants(productId: Long, variants: List<ProductVariant>) {
        viewModelScope.launch {
            productRepository.deleteVariantsByProduct(productId)
            val variantsWithId = variants.map { it.copy(productId = productId, userId = userId) }
            productRepository.insertVariants(variantsWithId)
        }
    }
}
