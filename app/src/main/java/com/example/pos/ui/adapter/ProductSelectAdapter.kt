package com.example.pos.ui.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.data.entity.Product
import com.example.pos.databinding.ItemProductSelectBinding
import com.example.pos.utils.CurrencyFormatter

class ProductSelectAdapter(
    private val onProductClick: (Product) -> Unit
) : ListAdapter<Product, ProductSelectAdapter.ViewHolder>(ProductDiffCallback()) {

    private val quantities = mutableMapOf<Long, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductSelectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemProductSelectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = CurrencyFormatter.format(product.price)

            if (product.imagePath != null) {
                val bitmap = BitmapFactory.decodeFile(product.imagePath)
                binding.productImage.setImageBitmap(bitmap)
            } else {
                binding.productImage.setImageResource(com.example.pos.R.drawable.bg_button_secondary)
            }

            val qty = quantities[product.id] ?: 0
            binding.quantity.text = qty.toString()

            binding.btnPlus.setOnClickListener {
                quantities[product.id] = (quantities[product.id] ?: 0) + 1
                binding.quantity.text = quantities[product.id].toString()
                onProductClick(product)
            }

            binding.btnMinus.setOnClickListener {
                val currentQty = quantities[product.id] ?: 0
                if (currentQty > 0) {
                    quantities[product.id] = currentQty - 1
                    binding.quantity.text = quantities[product.id].toString()
                }
            }
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
