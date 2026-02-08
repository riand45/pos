package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    QUEUE, // Antrian
    PROCESS, // Proses
    DONE, // Selesai
    ARCHIVED // History
}

@Serializable
@Entity(tableName = "orders")
data class Order(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @SerialName("order_number") val orderNumber: String,
        val status: OrderStatus = OrderStatus.QUEUE,
        @SerialName("total_items") val totalItems: Int = 0,
        @SerialName("total_price") val totalPrice: Double = 0.0,
        @SerialName("customer_name") val customerName: String? = null,
        @SerialName("table_info") val tableInfo: String? = null,
        @SerialName("created_at") val createdAt: Long = System.currentTimeMillis(),
        @SerialName("updated_at") val updatedAt: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
