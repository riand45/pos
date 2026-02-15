package com.example.pos.ui.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.databinding.FragmentCustomerListBinding

class CustomerListFragment : Fragment() {

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

        val adapter = CustomerAdapter { customer ->
            val action = CustomerListFragmentDirections.actionCustomersToDetail(customer.id)
            findNavController().navigate(action)
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

        binding.fabAddCustomer.setOnClickListener {
            val action = CustomerListFragmentDirections.actionCustomersToDetail(-1L)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
