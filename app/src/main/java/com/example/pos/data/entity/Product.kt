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
        tableName = "products",
        foreignKeys =
                [
                        ForeignKey(
                                entity = Category::class,
                                parentColumns = ["id"],
                                childColumns = ["categoryId"],
                                onDelete = ForeignKey.CASCADE
                        )],
        indices = [Index("categoryId")]
)
data class Product(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @SerialName("category_id") val categoryId: Long,
        val name: String,
        val price: Double,
        val stock: Int? = null,
        @SerialName("image_path") val imagePath: String? = null,
        @SerialName("is_new") val isNew: Boolean = false,
        @SerialName("created_at") val createdAt: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "user_id") @SerialName("user_id") val userId: String = ""
)
