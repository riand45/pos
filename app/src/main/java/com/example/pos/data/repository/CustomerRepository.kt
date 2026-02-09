package com.example.pos.data.repository

import com.example.pos.data.dao.CustomerDao
import com.example.pos.data.entity.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {

    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    suspend fun insert(customer: Customer) {
        customerDao.insert(customer)
    }

    suspend fun update(customer: Customer) {
        customerDao.update(customer)
    }

    suspend fun delete(customer: Customer) {
        customerDao.delete(customer)
    }
    
    suspend fun getCustomerById(id: Long): Customer? {
        return customerDao.getCustomerById(id)
    }

    fun searchCustomers(query: String): Flow<List<Customer>> {
        return customerDao.searchCustomers(query)
    }
}
