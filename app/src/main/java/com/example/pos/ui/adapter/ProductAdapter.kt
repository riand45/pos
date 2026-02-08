package com.example.pos.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.entity.Product
import com.example.pos.databinding.ItemProductBinding
import com.example.pos.utils.CurrencyFormatter
import android.graphics.BitmapFactory
import java.io.File

class ProductAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val context = binding.root.context

            binding.productName.text = product.name
            binding.productPrice.text = CurrencyFormatter.format(product.price)
            binding.productCategory.text = "Category" // Would need join query for actual category name

            val stock = product.stock ?: 0
            binding.stockValue.text = "$stock units"

            // Stock status indicator
            when {
                stock <= 0 -> {
                    binding.stockIndicator.setBackgroundResource(R.drawable.indicator_queue)
                    binding.stockValue.setTextColor(ContextCompat.getColor(context, R.color.status_error))
                    binding.lowStockBadge.visibility = View.VISIBLE
                    binding.lowStockBadge.text = "OUT OF STOCK"
                }
                stock <= 10 -> {
                    binding.stockIndicator.setBackgroundResource(R.drawable.indicator_process)
                    binding.stockValue.setTextColor(ContextCompat.getColor(context, R.color.status_warning))
                    binding.lowStockBadge.visibility = View.VISIBLE
                    binding.lowStockBadge.text = "LOW STOCK"
                }
                else -> {
                    binding.stockIndicator.setBackgroundResource(R.drawable.indicator_done)
                    binding.stockValue.setTextColor(ContextCompat.getColor(context, R.color.status_success))
                    binding.lowStockBadge.visibility = View.GONE
                }
            }

            // Load product image
            product.imagePath?.let { path ->
                val imgFile = File(path)
                if (imgFile.exists()) {
                    val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    binding.productImage.setImageBitmap(bitmap)
                } else {
                    binding.productImage.setImageResource(R.drawable.ic_product)
                }
            } ?: run {
                binding.productImage.setImageResource(R.drawable.ic_product)
            }

            binding.productBadge.visibility = View.GONE

            binding.root.setOnClickListener { onProductClick(product) }
            binding.btnEdit.setOnClickListener { onProductClick(product) }
            binding.btnDelete.setOnClickListener { onDeleteClick(product) }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
