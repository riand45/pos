package com.example.pos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.entity.PaymentMethod
import com.example.pos.data.entity.Transaction
import com.example.pos.databinding.ItemTransactionBinding
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter

class TransactionAdapter(
    private val onTransactionClick: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            val context = binding.root.context

            binding.orderId.text = transaction.orderNumber
            binding.transactionTime.text = DateFormatter.formatTime(transaction.createdAt)
            binding.customerName.text = transaction.customerName ?: "Guest"

            val paymentLabel = when (transaction.paymentMethod) {
                PaymentMethod.CASH -> "Cash"
                PaymentMethod.TRANSFER -> transaction.bankName ?: "Transfer"
                PaymentMethod.QRIS -> "QRIS"
            }
            binding.paymentMethod.text = paymentLabel

            binding.transactionStatus.text = if (transaction.isRefunded) "Refunded" else "Success"
            binding.transactionStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (transaction.isRefunded) R.color.status_error else R.color.status_success
                )
            )

            binding.transactionTotal.text = CurrencyFormatter.format(transaction.totalAmount)
            
            binding.root.setOnClickListener { onTransactionClick(transaction) }
        }
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
}
