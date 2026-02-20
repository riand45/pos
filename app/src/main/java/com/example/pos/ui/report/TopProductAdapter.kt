package com.example.pos.ui.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.data.entity.ProductSalesReport
import com.example.pos.databinding.ItemReportTopProductBinding
import com.example.pos.utils.CurrencyFormatter

class TopProductAdapter : ListAdapter<ProductSalesReport, TopProductAdapter.ViewHolder>(ProductSalesReportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReportTopProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemReportTopProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report: ProductSalesReport) {
            binding.productName.text = report.productName
            binding.totalSales.text = CurrencyFormatter.format(report.totalSales)
            binding.salesCount.text = "${report.totalQuantity} terjual"
        }
    }

    class ProductSalesReportDiffCallback : DiffUtil.ItemCallback<ProductSalesReport>() {
        override fun areItemsTheSame(oldItem: ProductSalesReport, newItem: ProductSalesReport): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductSalesReport, newItem: ProductSalesReport): Boolean {
            return oldItem == newItem
        }
    }
}
