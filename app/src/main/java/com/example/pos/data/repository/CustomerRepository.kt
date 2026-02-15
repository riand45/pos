package com.example.pos.data.repository

import com.example.pos.data.dao.CustomerDao
import com.example.pos.data.entity.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {

    fun getAllCustomers(userId: String): Flow<List<Customer>> {
        return customerDao.getAllCustomers(userId)
    }

    suspend fun insert(customer: Customer): Long {
        return customerDao.insert(customer)
    }

    suspend fun insertAndGet(customer: Customer): Customer {
        val id = customerDao.insert(customer)
        return customer.copy(id = id)
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

    fun searchCustomers(userId: String, query: String): Flow<List<Customer>> {
        return customerDao.searchCustomers(userId, query)
    }
}
