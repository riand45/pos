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
import com.example.pos.data.entity.Expense

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

        binding.btnFilterDate.setOnClickListener { showDateRangePicker() }

        setupChart()
    }

    private fun setupChart() {
        binding.expenseChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                textColor = android.graphics.Color.WHITE
                position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
            }
            
            axisLeft.apply {
                textColor = android.graphics.Color.WHITE
                setDrawGridLines(true)
                gridColor = android.graphics.Color.DKGRAY
            }
            
            axisRight.isEnabled = false
            legend.textColor = android.graphics.Color.WHITE
        }
    }

    private fun showDateRangePicker() {
        val picker = com.google.android.material.datepicker.MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Date Range")
            .build()

        picker.addOnPositiveButtonClickListener { range ->
            viewModel.setDateRange(range.first, range.second)
        }
        picker.show(childFragmentManager, "date_range_picker")
    }

    private fun observeExpenses() {
        viewModel.filteredExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses)
            updateChart(expenses)
            updateCategoryChips(expenses)
        }

        viewModel.startDate.observe(viewLifecycleOwner) { start ->
            val end = viewModel.endDate.value
            if (start != null && end != null) {
                binding.btnFilterDate.text = "${com.example.pos.utils.DateFormatter.formatShortDate(start)} - ${com.example.pos.utils.DateFormatter.formatShortDate(end)}"
            } else {
                binding.btnFilterDate.text = "All Time"
            }
        }
    }

    private fun updateCategoryChips(expenses: List<Expense>) {
        if (binding.chipGroupCategory.childCount > 1) return // Already populated, '1' because of 'All'

        val categories = listOf("All") + expenses.map { it.category }.distinct()
        binding.chipGroupCategory.removeAllViews()
        
        categories.forEach { category ->
            val chip = com.google.android.material.chip.Chip(requireContext()).apply {
                text = category
                isCheckable = true
                isChecked = (category == "All")
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) viewModel.setCategoryFilter(category)
                }
            }
            binding.chipGroupCategory.addView(chip)
        }
    }

    private fun updateChart(expenses: List<Expense>) {
        if (expenses.isEmpty()) {
            binding.expenseChart.clear()
            return
        }

        val entries = expenses.sortedBy { it.date }
            .groupBy { com.example.pos.utils.DateFormatter.formatShortDate(it.date) }
            .map { (date, items) -> 
                val total = items.sumOf { it.amount }
                date to total
            }

        val dataEntries = entries.mapIndexed { index, (date, total) ->
            com.github.mikephil.charting.data.Entry(index.toFloat(), total.toFloat())
        }

        val dataSet = com.github.mikephil.charting.data.LineDataSet(dataEntries, "Expenses").apply {
            color = android.graphics.Color.CYAN
            setCircleColor(android.graphics.Color.CYAN)
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(false)
            valueTextColor = android.graphics.Color.WHITE
            mode = com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER
        }

        binding.expenseChart.data = com.github.mikephil.charting.data.LineData(dataSet)
        binding.expenseChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
