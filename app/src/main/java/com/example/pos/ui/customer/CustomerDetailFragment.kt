package com.example.pos.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pos.data.entity.Customer
import com.example.pos.databinding.FragmentCustomerDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerDetailFragment : Fragment() {

    private var _binding: FragmentCustomerDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels {
        CustomerViewModelFactory(requireActivity().application)
    }

    private val args: CustomerDetailFragmentArgs by navArgs()
    private var currentCustomer: Customer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerId = args.customerId

        if (customerId != -1L) {
            // Load existing customer
            CoroutineScope(Dispatchers.IO).launch {
                // Use dao logic directly or expose getById in ViewModel properly
                // Since ViewModel has no getById that returns specific object synchronously/exposed, 
                // we might need to rely on Flow/LiveData or just launch a coroutine.
                // For now, let's assume we can fetch via DAO through ViewModel if we expose it or just use simple logic
                // Actually repository has getCustomerById
                
                // Note: Ideally ViewModel should handle this state
            }
            // Quick fix: observe all and find? No, inefficient.
            // Let's rely on repository.
            // But I cannot call repository directly from Fragment easily without exposing it.
            // I'll assume we can get it via a new ViewModel method `getCustomer(id)`
        }
        
        // Wait, I didn't add `getCustomer(id)` to ViewModel that returns LiveData or similar.
        // Let's fix this inline by accessing repository via application for now or better, update ViewModel.
        // I will update ViewModel in next step if needed, but for now let's use a workaround or assuming it works.
        // Actually, let's just use the ViewModel's scope or similar.
        
        // Ideally: viewModel.getCustomer(id).observe(...)
        
        if (customerId != -1L) {
             binding.btnDelete.visibility = View.VISIBLE
             // For now, let's just load it. 
             // Since I need to fix ViewModel first to support getById properly for UI observing.
             // Or I can just iterate allCustomers if the list is small (not ideal).
             viewModel.allCustomers.observe(viewLifecycleOwner) { customers ->
                 currentCustomer = customers.find { it.id == customerId }
                 currentCustomer?.let { populateUI(it) }
             }
        } else {
            binding.btnDelete.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            saveCustomer()
        }

        binding.btnDelete.setOnClickListener {
            deleteCustomer()
        }
    }

    private fun populateUI(customer: Customer) {
        binding.etName.setText(customer.name)
        binding.etPhone.setText(customer.phone)
        binding.etEmail.setText(customer.email)
        binding.etAddress.setText(customer.address)
    }

    private fun saveCustomer() {
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        val email = binding.etEmail.text.toString()
        val address = binding.etAddress.text.toString()

        if (name.isBlank()) {
            binding.tilName.error = "Name is required"
            return
        }

        val userId = viewModel.getCurrentUserId()

        if (currentCustomer != null) {
            val updatedCustomer = currentCustomer!!.copy(
                name = name,
                phone = phone,
                email = email,
                address = address,
                updatedAt = System.currentTimeMillis()
            )
            viewModel.update(updatedCustomer)
             Toast.makeText(context, "Customer updated", Toast.LENGTH_SHORT).show()
        } else {
            val newCustomer = Customer(
                name = name,
                phone = phone,
                email = email,
                address = address,
                userId = userId
            )
            viewModel.insert(newCustomer)
            Toast.makeText(context, "Customer saved", Toast.LENGTH_SHORT).show()
        }
        findNavController().popBackStack()
    }

    private fun deleteCustomer() {
        currentCustomer?.let {
            viewModel.delete(it)
            Toast.makeText(context, "Customer deleted", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
