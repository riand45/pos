package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "expenses")
data class Expense(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val title: String,
        val amount: Double,
        val category: String,
        val note: String? = null,
        val date: Long = System.currentTimeMillis(),
        @SerialName("created_at") val createdAt: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
