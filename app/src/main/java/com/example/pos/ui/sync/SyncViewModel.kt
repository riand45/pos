package com.example.pos.ui.sync

import android.app.Application
import androidx.lifecycle.*
import com.example.pos.PosApplication
import com.example.pos.data.repository.SyncRepository
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class SyncViewModel(application: Application) : AndroidViewModel(application) {
    private val syncRepository: SyncRepository by lazy {
        val app = getApplication<PosApplication>()
        SyncRepository(
                app.categoryRepository.categoryDao, // I need to check if repo exposes dao
                app.productRepository.productDao,
                app.orderRepository.orderDao,
                app.orderRepository.orderItemDao,
                app.expenseRepository.expenseDao,
                app.database.customerDao(),
                app.transactionRepository.transactionDao,
                app.supabase
        )
    }

    private val _syncStatus = MutableLiveData<String>()
    val syncStatus: LiveData<String> = _syncStatus

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun backup() {
        val userId = getApplication<PosApplication>().supabase.auth.currentUserOrNull()?.id ?: ""
        viewModelScope.launch {
            _isLoading.value = true
            _syncStatus.value = "Starting backup to Supabase..."
            try {
                syncRepository.backupData(userId)
                _syncStatus.value = "Backup successful!"
            } catch (e: Exception) {
                _syncStatus.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun import() {
        val userId = getApplication<PosApplication>().supabase.auth.currentUserOrNull()?.id ?: ""
        viewModelScope.launch {
            _isLoading.value = true
            _syncStatus.value = "Fetching data from Supabase..."
            try {
                syncRepository.importData(userId)
                _syncStatus.value = "Import successful!"
            } catch (e: Exception) {
                _syncStatus.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
