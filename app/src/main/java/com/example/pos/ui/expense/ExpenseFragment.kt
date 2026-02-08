package com.example.pos.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.databinding.FragmentExpenseBinding
import com.example.pos.ui.adapter.ExpenseAdapter

class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeExpenses()
    }

    private fun setupUI() {
        expenseAdapter =
                ExpenseAdapter(
                        onExpenseClick = { expense ->
                            ExpenseEditDialogFragment.newInstance(expense)
                                    .show(childFragmentManager, "edit_expense")
                        },
                        onDeleteClick = { expense ->
                            AlertDialog.Builder(requireContext(), R.style.PosDialogStyle)
                                    .setTitle("Delete Expense")
                                    .setMessage(
                                            "Are you sure you want to delete '${expense.title}'?"
                                    )
                                    .setPositiveButton("Delete") { _, _ ->
                                        viewModel.delete(expense)
                                    }
                                    .setNegativeButton("Cancel", null)
                                    .show()
                        }
                )

        binding.expenseRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseAdapter
        }

        binding.btnAddExpense.setOnClickListener {
            ExpenseEditDialogFragment.newInstance().show(childFragmentManager, "add_expense")
        }
    }

    private fun observeExpenses() {
        viewModel.getAllExpenses().observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
