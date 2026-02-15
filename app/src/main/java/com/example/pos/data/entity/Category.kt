package com.example.pos.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val name: String,
        val description: String? = null,
        @SerialName("icon_name") val iconName: String = "restaurant",
        @SerialName("product_count") val productCount: Int = 0,
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
