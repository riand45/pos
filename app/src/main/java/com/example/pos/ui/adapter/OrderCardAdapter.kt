package com.example.pos.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.entity.Order
import com.example.pos.data.entity.OrderStatus
import com.example.pos.data.entity.OrderWithItems
import com.example.pos.databinding.ItemOrderCardBinding
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter

class OrderCardAdapter(
    private val onCardClick: (Order) -> Unit,
    private val onNextClick: (Order) -> Unit,
    private val onEditClick: (Order) -> Unit = {},
    private val onDeleteClick: (Order) -> Unit = {},
    private val onArchiveClick: (Order) -> Unit = {},
    private val onLongClick: ((View, Order) -> Boolean)? = null,
    private val showProgress: Boolean = false,
    private val showNextButton: Boolean = true
) : ListAdapter<OrderWithItems, OrderCardAdapter.ViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemOrderCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wrapper: OrderWithItems) {
            val order = wrapper.order
            binding.orderNumber.text = order.orderNumber
            binding.orderTime.text = DateFormatter.getRelativeTime(order.createdAt)
            binding.orderProductName.text = getProductSummary(wrapper)
            binding.orderInfo.text = "${order.totalItems} Items • ${order.tableInfo ?: "Dine-In"}"
            binding.orderPrice.text = CurrencyFormatter.format(order.totalPrice)

            // Progress bar visibility
            binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE

            // Action/Next button visibility and icons
            when (order.status) {
                OrderStatus.PROCESS -> {
                    binding.btnAction.visibility = View.VISIBLE
                    binding.btnAction.text = binding.root.context.getString(R.string.finish)
                    binding.btnNext.visibility = View.GONE
                }
                OrderStatus.DONE -> {
                    binding.btnAction.visibility = View.GONE
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnNext.setImageResource(R.drawable.ic_history)
                    binding.btnNext.contentDescription = "Move to History"
                }
                else -> { // QUEUE
                    binding.btnAction.visibility = View.GONE
                    binding.btnNext.visibility = if (showNextButton) View.VISIBLE else View.GONE
                    binding.btnNext.setImageResource(R.drawable.ic_arrow_right)
                    binding.btnNext.contentDescription = "Next"
                }
            }

            // Edit/Delete button visibility (hidden for DONE)
            val actionsVisible = order.status != OrderStatus.DONE
            binding.btnEdit.visibility = if (actionsVisible) View.VISIBLE else View.GONE
            binding.btnDelete.visibility = if (actionsVisible) View.VISIBLE else View.GONE

            binding.root.setOnClickListener { onCardClick(order) }
            binding.btnAction.setOnClickListener { onNextClick(order) }
            binding.btnNext.setOnClickListener { 
                if (order.status == OrderStatus.DONE) onArchiveClick(order) else onNextClick(order)
            }
            binding.btnEdit.setOnClickListener { onEditClick(order) }
            binding.btnDelete.setOnClickListener { onDeleteClick(order) }
            
            if (onLongClick != null) {
                binding.root.setOnLongClickListener { view -> onLongClick.invoke(view, order) }
            }
        }

        private fun getProductSummary(wrapper: OrderWithItems): String {
            if (wrapper.items.isEmpty()) return "No items"
            return wrapper.items.joinToString(", ") { it.productName }
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<OrderWithItems>() {
        override fun areItemsTheSame(oldItem: OrderWithItems, newItem: OrderWithItems): Boolean {
            return oldItem.order.id == newItem.order.id
        }

        override fun areContentsTheSame(oldItem: OrderWithItems, newItem: OrderWithItems): Boolean {
            return oldItem == newItem
        }
    }
}
