package com.example.pos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.databinding.ItemNavBinding
import com.example.pos.ui.MainActivity

class NavAdapter(
    private val items: List<MainActivity.NavItem>,
    private val onItemClick: (MainActivity.NavItem) -> Unit
) : RecyclerView.Adapter<NavAdapter.ViewHolder>() {

    private var selectedId: Int = -1

    fun setSelectedId(id: Int) {
        val oldSelectedPos = items.indexOfFirst { it.destinationId == selectedId }
        val newSelectedPos = items.indexOfFirst { it.destinationId == id }
        selectedId = id
        if (oldSelectedPos >= 0) notifyItemChanged(oldSelectedPos)
        if (newSelectedPos >= 0) notifyItemChanged(newSelectedPos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNavBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(
        private val binding: ItemNavBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainActivity.NavItem) {
            val isSelected = item.destinationId == selectedId
            val context = binding.root.context

            binding.navTitle.text = item.title
            binding.navIcon.setImageResource(item.iconRes)

            val textColor = if (isSelected) {
                ContextCompat.getColor(context, R.color.accent_primary)
            } else {
                ContextCompat.getColor(context, R.color.text_secondary)
            }
            
            binding.navTitle.setTextColor(textColor)
            binding.navIcon.setColorFilter(textColor)

            if (isSelected) {
                binding.root.setBackgroundResource(R.drawable.bg_nav_selected)
            } else {
                binding.root.setBackgroundResource(android.R.color.transparent)
            }

            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
