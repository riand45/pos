package com.example.pos.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithItems(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>
)
