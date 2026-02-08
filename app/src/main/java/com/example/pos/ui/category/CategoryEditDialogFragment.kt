package com.example.pos.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.pos.R
import com.example.pos.data.entity.Category
import com.example.pos.databinding.DialogEditCategoryBinding

class CategoryEditDialogFragment : DialogFragment() {

    private var _binding: DialogEditCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()
    private var category: Category? = null

    companion object {
        fun newInstance(category: Category? = null): CategoryEditDialogFragment {
            val fragment = CategoryEditDialogFragment()
            fragment.category = category
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.PosDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        category?.let {
            binding.dialogTitle.text = getString(R.string.edit_category)
            binding.inputName.setText(it.name)
            binding.inputDescription.setText(it.description)
        }

        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {
            val name = binding.inputName.text.toString()
            val description = binding.inputDescription.text.toString().ifEmpty { null }

            if (name.isBlank()) {
                binding.layoutName.error = "Name is required"
                return@setOnClickListener
            }

            if (category == null) {
                viewModel.insert(name, description)
            } else {
                viewModel.update(category!!, name, description)
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
