package com.example.pos.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.databinding.FragmentReportBinding
import com.example.pos.ui.adapter.ProductAdapter
import com.example.pos.ui.adapter.TransactionAdapter
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.core.util.Pair

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by viewModels()

    private lateinit var topProductAdapter: TopProductAdapter
    private lateinit var lowStockAdapter: ProductAdapter
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerViews() {
        topProductAdapter = TopProductAdapter()
        binding.rvTopProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = topProductAdapter
        }

        lowStockAdapter = ProductAdapter(
            onProductClick = { /* Navigate to edit if needed */ },
            onDeleteClick = { /* Handle delete if needed */ }
        )
        binding.rvLowStock.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lowStockAdapter
        }

        transactionAdapter = TransactionAdapter(
            onTransactionClick = { /* Navigate to detail if needed */ }
        )
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
    }

    private fun setupObservers() {
        viewModel.startDate.observe(viewLifecycleOwner) { start ->
            updateDateDisplay()
        }

        viewModel.endDate.observe(viewLifecycleOwner) { end ->
            updateDateDisplay()
        }

        viewModel.totalRevenue.observe(viewLifecycleOwner) { revenue ->
            binding.tvTotalRevenue.text = CurrencyFormatter.format(revenue ?: 0.0)
        }

        viewModel.totalProfit.observe(viewLifecycleOwner) { profit ->
            binding.tvTotalProfit.text = CurrencyFormatter.format(profit ?: 0.0)
        }

        viewModel.topProducts.observe(viewLifecycleOwner) { products ->
            topProductAdapter.submitList(products)
        }

        viewModel.lowStockProducts.observe(viewLifecycleOwner) { products ->
            lowStockAdapter.submitList(products)
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.submitList(transactions)
        }
    }

    private fun updateDateDisplay() {
        val startStr = DateFormatter.formatDateShort(viewModel.startDate.value ?: 0)
        val endStr = DateFormatter.formatDateShort(viewModel.endDate.value ?: 0)
        binding.tvDateRange.text = if (startStr == endStr) startStr else "$startStr - $endStr"
    }

    private fun setupListeners() {
        binding.btnChangeDate.setOnClickListener {
            showDateRangePicker()
        }
    }

    private fun showDateRangePicker() {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Pilih Rentang Tanggal")
            .setSelection(
                Pair(
                    viewModel.startDate.value,
                    viewModel.endDate.value
                )
            )
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            viewModel.setDateRange(selection.first, selection.second)
        }
        picker.show(childFragmentManager, "DATE_RANGE_PICKER")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
