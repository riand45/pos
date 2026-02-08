package com.example.pos.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.data.entity.Category
import com.example.pos.databinding.FragmentCategoryBinding
import com.example.pos.ui.adapter.CategoryAdapter

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeCategories()
    }

    private fun setupUI() {
        categoryAdapter =
                CategoryAdapter(
                        onEditClick = { category -> showEditDialog(category) },
                        onDeleteClick = { category -> showDeleteConfirm(category) }
                )

        binding.categoriesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }

        binding.btnAddCategory.setOnClickListener { showEditDialog(null) }
    }

    private fun showEditDialog(category: Category?) {
        CategoryEditDialogFragment.newInstance(category).show(childFragmentManager, "edit_category")
    }

    private fun showDeleteConfirm(category: Category) {
        AlertDialog.Builder(requireContext(), R.style.PosDialogStyle)
                .setTitle(R.string.action_delete)
                .setMessage("Are you sure you want to delete category '${category.name}'?")
                .setPositiveButton(R.string.action_delete) { _, _ -> viewModel.delete(category) }
                .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    private fun observeCategories() {
        viewModel.getAllCategories().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
            binding.categoryCount.text = categories.size.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
