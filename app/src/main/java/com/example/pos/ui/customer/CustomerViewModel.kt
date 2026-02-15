package com.example.pos.ui.customer

import androidx.lifecycle.*
import com.example.pos.PosApplication
import com.example.pos.data.entity.Customer
import com.example.pos.data.repository.CustomerRepository
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class CustomerViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val posApp = application as PosApplication
    private val repository: CustomerRepository
    val allCustomers: LiveData<List<Customer>>

    init {
        val database = posApp.database
        val customerDao = database.customerDao()
        repository = CustomerRepository(customerDao)
        allCustomers = posApp.currentUserId.switchMap { uid ->
            val activeUid = if (uid.isNotEmpty()) uid else getCurrentUserId()
            repository.getAllCustomers(activeUid).asLiveData()
        }
    }

    fun searchCustomers(query: String): LiveData<List<Customer>> {
        return posApp.currentUserId.switchMap { uid ->
            val activeUid = if (uid.isNotEmpty()) uid else getCurrentUserId()
            repository.searchCustomers(activeUid, query).asLiveData()
        }
    }

    fun getCustomerById(id: Long): LiveData<Customer?> {
        val liveData = MutableLiveData<Customer?>()
        viewModelScope.launch {
            liveData.postValue(repository.getCustomerById(id))
        }
        return liveData
    }

    fun insert(customer: Customer) = viewModelScope.launch {
        repository.insert(customer)
    }

    fun insertAndGet(customer: Customer, onComplete: (Customer) -> Unit) = viewModelScope.launch {
        val insertedCustomer = repository.insertAndGet(customer)
        onComplete(insertedCustomer)
    }

    fun update(customer: Customer) = viewModelScope.launch {
        repository.update(customer)
    }

    fun delete(customer: Customer) = viewModelScope.launch {
        repository.delete(customer)
    }
    
    fun getCurrentUserId(): String {
        return getApplication<PosApplication>().supabase.auth.currentUserOrNull()?.id ?: ""
    }
}

class CustomerViewModelFactory(private val application: android.app.Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
