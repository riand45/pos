package com.example.pos.data.repository

import androidx.lifecycle.LiveData
import com.example.pos.data.dao.CategoryDao
import com.example.pos.data.entity.Category

class CategoryRepository(val categoryDao: CategoryDao) {
    fun getAllCategories(userId: String): LiveData<List<Category>> =
            categoryDao.getAllCategories(userId)

    suspend fun insert(category: Category): Long = categoryDao.insert(category)

    suspend fun update(category: Category) = categoryDao.update(category)

    suspend fun delete(category: Category) = categoryDao.delete(category)

    suspend fun deleteById(id: Long) = categoryDao.deleteById(id)

    suspend fun getCategoryById(id: Long): Category? = categoryDao.getCategoryById(id)

    suspend fun getAllCategoriesList(userId: String): List<Category> =
            categoryDao.getAllCategoriesList(userId)
}
