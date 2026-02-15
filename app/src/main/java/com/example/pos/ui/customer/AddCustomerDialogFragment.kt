package com.example.pos.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.pos.R
import com.example.pos.data.entity.Customer
import com.example.pos.databinding.DialogAddCustomerBinding

class AddCustomerDialogFragment(
    private val onCustomerAdded: (Customer) -> Unit
) : DialogFragment() {

    private var _binding: DialogAddCustomerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by viewModels {
        CustomerViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            saveCustomer()
        }
    }

    private fun saveCustomer() {
        val name = binding.etName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()

        if (name.isBlank()) {
            binding.tilName.error = "Name is required"
            return
        }

        binding.tilName.error = null

        val userId = viewModel.getCurrentUserId()

        val newCustomer = Customer(
            name = name,
            phone = phone,
            email = email,
            address = address,
            userId = userId
        )

        // Insert and get the ID back
        viewModel.insertAndGet(newCustomer) { insertedCustomer ->
            activity?.runOnUiThread {
                Toast.makeText(context, "Customer added", Toast.LENGTH_SHORT).show()
                onCustomerAdded(insertedCustomer)
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
