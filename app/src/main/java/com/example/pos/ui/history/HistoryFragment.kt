package com.example.pos.ui.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.data.entity.Transaction
import com.example.pos.databinding.FragmentHistoryBinding
import com.example.pos.ui.adapter.TransactionAdapter
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter
import com.example.pos.utils.ExportUtils
import androidx.fragment.app.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.core.util.Pair
import kotlinx.coroutines.launch
import java.util.*

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionAdapter: TransactionAdapter
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        transactionAdapter = TransactionAdapter { transaction ->
            showTransactionDetail(transaction)
        }

        binding.transactionsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        binding.btnDate.setOnClickListener { showDateRangePicker() }
        binding.btnChangeDate.setOnClickListener { showDateRangePicker() }

        binding.btnExportCsv.setOnClickListener { exportToCsv() }
        binding.btnExportPdf.setOnClickListener { exportToPdf() }

        binding.filterAll.setOnClickListener { viewModel.setFilter("All") }
        binding.filterSuccess.setOnClickListener { viewModel.setFilter("Success") }
        binding.filterRefund.setOnClickListener { viewModel.setFilter("Refund") }
    }

    private fun showTransactionDetail(transaction: Transaction) {
        TransactionDetailDialogFragment.newInstance(transaction)
            .show(childFragmentManager, "transaction_detail")
    }

    private fun observeViewModel() {
        viewModel.startDate.observe(viewLifecycleOwner) { start ->
            val end = viewModel.endDate.value ?: start
            binding.btnDate.text = "${DateFormatter.formatShortDate(start)} - ${DateFormatter.formatShortDate(end)}"
        }

        viewModel.endDate.observe(viewLifecycleOwner) { end ->
            val start = viewModel.startDate.value ?: end
            binding.btnDate.text = "${DateFormatter.formatShortDate(start)} - ${DateFormatter.formatShortDate(end)}"
        }

        viewModel.filterStatus.observe(viewLifecycleOwner) { status ->
            updateFilterChips(status)
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.submitList(transactions)
            
            // Update stats
            val totalRevenue = transactions.sumOf { it.totalAmount }
            val orderCount = transactions.size
            val avgBasket = if (orderCount > 0) totalRevenue / orderCount else 0.0

            binding.totalRevenue.text = CurrencyFormatter.format(totalRevenue)
            binding.totalOrders.text = "$orderCount Orders"
            binding.avgBasket.text = "Avg: ${CurrencyFormatter.format(avgBasket)}"
        }

        viewModel.monthlyReport.observe(viewLifecycleOwner) { stats ->
            binding.monthlyRevenue.text = CurrencyFormatter.format(stats.revenue)
        }

        viewModel.yearlyReport.observe(viewLifecycleOwner) { stats ->
            binding.yearlyRevenue.text = CurrencyFormatter.format(stats.revenue)
        }
    }

    private fun updateFilterChips(activeStatus: String) {
        val whiteText = ContextCompat.getColor(requireContext(), R.color.background_primary)
        val darkText = ContextCompat.getColor(requireContext(), R.color.text_secondary)

        binding.filterAll.apply {
            isSelected = activeStatus == "All"
            setTextColor(if (isSelected) whiteText else darkText)
        }

        binding.filterSuccess.apply {
            isSelected = activeStatus == "Success"
            setTextColor(if (isSelected) whiteText else darkText)
        }

        binding.filterRefund.apply {
            isSelected = activeStatus == "Refund"
            setTextColor(if (isSelected) whiteText else darkText)
        }
    }

    private fun showDateRangePicker() {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Date Range")
            .setSelection(
                Pair(
                    viewModel.startDate.value ?: MaterialDatePicker.todayInUtcMilliseconds(),
                    viewModel.endDate.value ?: MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()

        picker.addOnPositiveButtonClickListener { range ->
            range.first?.let { start ->
                range.second?.let { end ->
                    viewModel.setDateRange(start, end)
                }
            }
        }
        picker.show(childFragmentManager, "date_range_picker")
    }

    private fun exportToCsv() {
        viewLifecycleOwner.lifecycleScope.launch {
            val transactionsWithItems = viewModel.getTransactionsWithItemsForExport()
            ExportUtils.exportToCsv(requireContext(), transactionsWithItems)
        }
    }

    private fun exportToPdf() {
        viewModel.transactions.value?.let { list ->
            ExportUtils.exportToPdf(requireContext(), list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
