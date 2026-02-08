package com.example.pos.ui.product

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.data.entity.Product
import com.example.pos.databinding.DialogEditProductBinding
import com.example.pos.utils.ImageUtils
import io.github.jan.supabase.gotrue.auth

class ProductEditDialogFragment : DialogFragment() {

    private var _binding: DialogEditProductBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private var product: Product? = null
    private var selectedCategoryId: Long = -1
    private var selectedImageUri: Uri? = null
    private var currentImagePath: String? = null

    private val pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    selectedImageUri = it
                    binding.productImage.setImageURI(it)
                }
            }

    companion object {
        fun newInstance(product: Product? = null): ProductEditDialogFragment {
            val fragment = ProductEditDialogFragment()
            fragment.product = product
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
        _binding = DialogEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadCategories()
    }

    private fun setupUI() {
        product?.let {
            binding.dialogTitle.text = getString(R.string.edit_product)
            binding.inputName.setText(it.name)
            binding.inputPrice.setText(it.price.toString())
            binding.inputStock.setText(it.stock?.toString() ?: "")
            selectedCategoryId = it.categoryId
            currentImagePath = it.imagePath

            it.imagePath?.let { path ->
                val imgFile = java.io.File(path)
                if (imgFile.exists()) {
                    val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    binding.productImage.setImageBitmap(bitmap)
                }
            }
        }

        binding.btnChangeImage.setOnClickListener { pickImageLauncher.launch("image/*") }

        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {
            val name = binding.inputName.text.toString()
            val priceStr = binding.inputPrice.text.toString()
            val stockStr = binding.inputStock.text.toString()

            if (name.isBlank() || priceStr.isBlank() || selectedCategoryId == -1L) {
                // Should show errors
                return@setOnClickListener
            }

            val price = priceStr.toDoubleOrNull() ?: 0.0
            val stock = stockStr.toIntOrNull()

            // Save image if selected
            selectedImageUri?.let {
                val path = ImageUtils.saveImageToInternalStorage(requireContext(), it)
                if (path != null) currentImagePath = path
            }

            if (product == null) {
                viewModel.insert(selectedCategoryId, name, price, stock, currentImagePath)
            } else {
                viewModel.update(
                        product!!,
                        selectedCategoryId,
                        name,
                        price,
                        stock,
                        currentImagePath
                )
            }
            dismiss()
        }
    }

    private fun loadCategories() {
        val app = requireActivity().application as PosApplication
        val userId = app.supabase.auth.currentUserOrNull()?.id ?: ""
        app.categoryRepository.getAllCategories(userId).observe(viewLifecycleOwner) { categories ->
            val adapter =
                    ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            categories.map { it.name }
                    )
            binding.inputCategory.setAdapter(adapter)

            if (selectedCategoryId != -1L) {
                val selectedCategory = categories.find { it.id == selectedCategoryId }
                selectedCategory?.let { binding.inputCategory.setText(it.name, false) }
            }

            binding.inputCategory.setOnItemClickListener { _, _, position, _ ->
                selectedCategoryId = categories[position].id
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
