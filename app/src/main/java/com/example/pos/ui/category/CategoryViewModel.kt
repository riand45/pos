package com.example.pos.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.pos.PosApplication
import com.example.pos.data.entity.Category
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val posApp = application as PosApplication
    private val categoryRepository = posApp.categoryRepository

    private val userId: String
        get() = posApp.supabase.auth.currentUserOrNull()?.id ?: ""

    fun getAllCategories(): LiveData<List<Category>> =
            posApp.currentUserId.switchMap { uid ->
                val activeUid = if (uid.isNotEmpty()) uid else userId
                categoryRepository.getAllCategories(activeUid)
            }

    fun insert(name: String, description: String? = null) {
        viewModelScope.launch {
            categoryRepository.insert(
                    Category(name = name, description = description, userId = userId)
            )
        }
    }

    fun update(category: Category, name: String, description: String? = null) {
        viewModelScope.launch {
            categoryRepository.update(
                    category.copy(name = name, description = description, userId = userId)
            )
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch { categoryRepository.delete(category) }
    }
}
