package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "product_variants",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId")]
)
data class ProductVariant(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerialName("product_id") val productId: Long,
    val name: String,
    val price: Double,
    val stock: Int? = null,
    val sku: String? = null,
    @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
