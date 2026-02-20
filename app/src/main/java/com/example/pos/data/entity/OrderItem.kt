package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductSalesReport(
    @SerialName("product_id") val productId: Long,
    @SerialName("product_name") val productName: String,
    @SerialName("total_quantity") val totalQuantity: Int,
    @SerialName("total_sales") val totalSales: Double
)

@Serializable
@Entity(
        tableName = "order_items",
        foreignKeys =
                [
                        ForeignKey(
                                entity = Order::class,
                                parentColumns = ["id"],
                                childColumns = ["orderId"],
                                onDelete = ForeignKey.CASCADE
                        ),
                        ForeignKey(
                                entity = Product::class,
                                parentColumns = ["id"],
                                childColumns = ["productId"],
                                onDelete = ForeignKey.CASCADE
                        )],
        indices = [Index("orderId"), Index("productId")]
)
data class OrderItem(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @SerialName("order_id") val orderId: Long,
        @SerialName("product_id") val productId: Long,
        @SerialName("product_name") val productName: String,
        val quantity: Int,
        @SerialName("unit_price") val unitPrice: Double,
        val cogs: Double = 0.0,
        @SerialName("total_price") val totalPrice: Double,
        @SerialName("net_income") val netIncome: Double = 0.0,
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
