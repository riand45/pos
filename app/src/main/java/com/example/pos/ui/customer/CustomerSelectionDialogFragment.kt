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
import com.example.pos.data.entity.Customer
import com.example.pos.databinding.FragmentCustomerListBinding

class CustomerSelectionDialogFragment(private val onCustomerSelected: (Customer) -> Unit) : DialogFragment() {

    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels {
        CustomerViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Hide FAB in selection mode
        binding.fabAddCustomer.visibility = View.GONE
        
        binding.tilSearch.hint = "Search Customer to Select"

        val adapter = CustomerAdapter { customer ->
            onCustomerSelected(customer)
            dismiss()
        }

        binding.rvCustomers.adapter = adapter
        binding.rvCustomers.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allCustomers.observe(viewLifecycleOwner) { customers ->
            adapter.submitList(customers)
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchCustomers(query).observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                } else {
                    viewModel.allCustomers.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
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
