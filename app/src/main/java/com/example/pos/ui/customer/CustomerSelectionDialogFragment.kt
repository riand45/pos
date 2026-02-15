package com.example.pos.ui.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.data.entity.Customer
import com.example.pos.databinding.DialogCustomerSelectionBinding

class CustomerSelectionDialogFragment(
    private val onCustomerSelected: (Customer) -> Unit
) : DialogFragment() {

    private var _binding: DialogCustomerSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels {
        CustomerViewModelFactory(requireActivity().application)
    }

    private lateinit var adapter: CustomerAdapter
    private var onAddNewCustomer: (() -> Unit)? = null

    companion object {
        fun newInstance(
            onCustomerSelected: (Customer) -> Unit,
            onAddNewCustomer: (() -> Unit)? = null
        ): CustomerSelectionDialogFragment {
            return CustomerSelectionDialogFragment(onCustomerSelected).apply {
                this.onAddNewCustomer = onAddNewCustomer
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomerSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupButtons()
        setupSearch()
        observeCustomers()
    }

    private fun setupRecyclerView() {
        adapter = CustomerAdapter { customer ->
            onCustomerSelected(customer)
            dismiss()
        }

        binding.rvCustomers.adapter = adapter
        binding.rvCustomers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupButtons() {
        // Close button in header
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        // Cancel button in footer
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        // Add new customer button in footer
        binding.btnAddCustomerFooter.setOnClickListener {
            showAddCustomerDialog()
        }

        // Add new customer button in empty state
        binding.btnAddNewCustomer.setOnClickListener {
            showAddCustomerDialog()
        }
    }

    private fun showAddCustomerDialog() {
        if (onAddNewCustomer != null) {
            dismiss()
            onAddNewCustomer?.invoke()
        } else {
            // Show inline add customer dialog
            val dialog = AddCustomerDialogFragment { newCustomer ->
                // After adding new customer, select it automatically
                onCustomerSelected(newCustomer)
                dismiss()
            }
            dialog.show(parentFragmentManager, "AddCustomer")
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchCustomers(query).observe(viewLifecycleOwner) { customers ->
                        updateList(customers)
                    }
                } else {
                    viewModel.allCustomers.observe(viewLifecycleOwner) { customers ->
                        updateList(customers)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeCustomers() {
        viewModel.allCustomers.observe(viewLifecycleOwner) { customers ->
            updateList(customers)
        }
    }

    private fun updateList(customers: List<Customer>) {
        adapter.submitList(customers)
        
        // Show/hide empty state
        if (customers.isEmpty()) {
            binding.rvCustomers.visibility = View.GONE
            binding.emptyState.visibility = View.VISIBLE
            
            // Update empty message based on search query
            val searchQuery = binding.etSearch.text.toString()
            if (searchQuery.isNotEmpty()) {
                binding.tvEmptyMessage.text = "No customers found for \"$searchQuery\""
            } else {
                binding.tvEmptyMessage.text = "No customers found"
            }
        } else {
            binding.rvCustomers.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
