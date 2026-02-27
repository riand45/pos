package com.example.pos.data.dao

import androidx.room.*
import com.example.pos.data.entity.ProductVariant
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductVariantDao {
    @Query("SELECT * FROM product_variants WHERE productId = :productId")
    fun getVariantsByProduct(productId: Long): Flow<List<ProductVariant>>

    @Query("SELECT * FROM product_variants WHERE productId = :productId")
    suspend fun getVariantsByProductSync(productId: Long): List<ProductVariant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(variant: ProductVariant): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(variants: List<ProductVariant>)

    @Update
    suspend fun update(variant: ProductVariant)

    @Delete
    suspend fun delete(variant: ProductVariant)

    @Query("DELETE FROM product_variants WHERE productId = :productId")
    suspend fun deleteByProduct(productId: Long)
}
