package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMethod {
        CASH,
        TRANSFER,
        QRIS
}

@Serializable
@Entity(
        tableName = "transactions",
        foreignKeys =
                [
                        ForeignKey(
                                entity = Order::class,
                                parentColumns = ["id"],
                                childColumns = ["orderId"],
                                onDelete = ForeignKey.CASCADE
                        )],
        indices = [Index("orderId")]
)
data class Transaction(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @SerialName("order_id") val orderId: Long,
        @SerialName("order_number") val orderNumber: String,
        @SerialName("payment_method") val paymentMethod: PaymentMethod,
        val subtotal: Double,
        val tax: Double = 0.0,
        @SerialName("total_amount") val totalAmount: Double,
        @SerialName("amount_paid") val amountPaid: Double,
        @SerialName("change_amount") val changeAmount: Double = 0.0,
        @SerialName("bank_name") val bankName: String? = null,
        @SerialName("customer_name") val customerName: String? = null,
        val status: String = "Success",
        @SerialName("is_refunded") val isRefunded: Boolean = false,
        @SerialName("created_at") val createdAt: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
