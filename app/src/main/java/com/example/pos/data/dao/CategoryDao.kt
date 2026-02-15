package com.example.pos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pos.data.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE user_id = :userId ORDER BY name ASC")
    fun getAllCategories(userId: String): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE user_id = :userId ORDER BY name ASC")
    suspend fun getAllCategoriesList(userId: String): List<Category>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(category: Category): Long

    @Update suspend fun update(category: Category)

    @Delete suspend fun delete(category: Category)

    @Query("DELETE FROM categories WHERE id = :id") suspend fun deleteById(id: Long)
}
