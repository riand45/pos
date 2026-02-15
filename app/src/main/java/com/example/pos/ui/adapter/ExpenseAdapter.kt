package com.example.pos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.data.entity.Expense
import com.example.pos.databinding.ItemExpenseBinding
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter

class ExpenseAdapter(
    private val onExpenseClick: (Expense) -> Unit,
    private val onDeleteClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.expenseTitle.text = expense.title
            binding.expenseCategoryDate.text = "${expense.category} • ${DateFormatter.formatLongDate(expense.date)}"
            binding.expenseAmount.text = "- ${CurrencyFormatter.format(expense.amount)}"

            binding.root.setOnClickListener { onExpenseClick(expense) }
            binding.btnDelete.setOnClickListener { onDeleteClick(expense) }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}
