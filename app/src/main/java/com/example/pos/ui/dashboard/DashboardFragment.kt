package com.example.pos.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pos.R
import com.example.pos.databinding.FragmentDashboardBinding
import com.example.pos.databinding.ItemDashboardStatBinding
import com.example.pos.utils.CurrencyFormatter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.btnNewOrder.setOnClickListener { findNavController().navigate(R.id.nav_orders) }

        binding.btnViewHistory.setOnClickListener { findNavController().navigate(R.id.nav_history) }

        binding.btnManageProducts.setOnClickListener {
            findNavController().navigate(R.id.nav_products)
        }

        // Setup stat card labels
        val revenueBinding = ItemDashboardStatBinding.bind(binding.statRevenue.root)
        revenueBinding.statLabel.text = "TODAY'S REVENUE"

        val ordersBinding = ItemDashboardStatBinding.bind(binding.statOrders.root)
        ordersBinding.statLabel.text = "ORDERS TODAY"

        val productsBinding = ItemDashboardStatBinding.bind(binding.statProducts.root)
        productsBinding.statLabel.text = "ACTIVE PRODUCTS"
    }

    private fun observeViewModel() {
        viewModel.getTodayStats().observe(viewLifecycleOwner) { transactions ->
            val totalRevenue = transactions.sumOf { it.totalAmount }
            val revenueBinding = ItemDashboardStatBinding.bind(binding.statRevenue.root)
            revenueBinding.statValue.text = CurrencyFormatter.format(totalRevenue)

            val ordersBinding = ItemDashboardStatBinding.bind(binding.statOrders.root)
            ordersBinding.statValue.text = transactions.size.toString()
        }

        viewModel.getActiveProductsCount().observe(viewLifecycleOwner) { count ->
            val productsBinding = ItemDashboardStatBinding.bind(binding.statProducts.root)
            productsBinding.statValue.text = count.toString()
        }

        viewModel.getWeeklyRevenue().observe(viewLifecycleOwner) { revenueMap ->
            setupChart(revenueMap)
        }
    }

    private fun setupChart(revenueMap: Map<String, Double>) {
        val entries = mutableListOf<BarEntry>()
        val labels = revenueMap.keys.toList()

        labels.forEachIndexed { index, date ->
            entries.add(BarEntry(index.toFloat(), revenueMap[date]?.toFloat() ?: 0f))
        }

        val dataSet = BarDataSet(entries, "Revenue")
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.accent_primary)
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
        dataSet.valueTextSize = 10f

        val barData = BarData(dataSet)
        binding.revenueChart.apply {
            data = barData
            description.isEnabled = false
            legend.isEnabled = false

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(labels)
                position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textColor = ContextCompat.getColor(requireContext(), R.color.text_tertiary)
            }

            axisLeft.apply {
                textColor = ContextCompat.getColor(requireContext(), R.color.text_tertiary)
                setDrawGridLines(true)
            }

            axisRight.isEnabled = false

            setScaleEnabled(false)
            animateY(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
