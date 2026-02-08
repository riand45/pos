package com.example.pos.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.pos.R
import com.example.pos.data.entity.Expense
import com.example.pos.databinding.DialogEditExpenseBinding

class ExpenseEditDialogFragment : DialogFragment() {

    private var _binding: DialogEditExpenseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()
    private var expense: Expense? = null

    companion object {
        fun newInstance(expense: Expense? = null): ExpenseEditDialogFragment {
            val fragment = ExpenseEditDialogFragment()
            fragment.expense = expense
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.PosDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        expense?.let {
            binding.dialogTitle.text = "Edit Expense"
            binding.inputTitle.setText(it.title)
            binding.inputAmount.setText(it.amount.toString())
            binding.inputCategory.setText(it.category)
        }

        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val amountStr = binding.inputAmount.text.toString()
            val category = binding.inputCategory.text.toString()

            if (title.isBlank() || amountStr.isBlank() || category.isBlank()) {
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull() ?: 0.0

            if (expense == null) {
                viewModel.insert(Expense(title = title, amount = amount, category = category))
            } else {
                viewModel.update(expense!!.copy(title = title, amount = amount, category = category))
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
