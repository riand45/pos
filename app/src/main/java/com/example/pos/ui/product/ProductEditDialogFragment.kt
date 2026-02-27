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
import com.example.pos.data.entity.ProductVariant
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
    private val variants = mutableListOf<ProductVariant>()

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
        setupVariantUI()
    }

    private fun setupUI() {
        product?.let {
            binding.dialogTitle.text = getString(R.string.edit_product)
            binding.inputName.setText(it.name)
            binding.inputPrice.setText(it.price.toString())
            binding.inputCogs.setText(it.cogs.toString())
            binding.inputStock.setText(it.stock?.toString() ?: "")
            binding.inputSku.setText(it.sku)
            binding.inputDiscountPrice.setText(it.discountPrice?.toString() ?: "")
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
            val cogsStr = binding.inputCogs.text.toString()
            val stockStr = binding.inputStock.text.toString()
            val sku = binding.inputSku.text.toString()
            val discountPriceStr = binding.inputDiscountPrice.text.toString()

            var hasError = false
            if (name.isBlank()) {
                binding.layoutName.error = "Name is required"
                hasError = true
            } else {
                binding.layoutName.error = null
            }

            if (priceStr.isBlank()) {
                binding.layoutPrice.error = "Price is required"
                hasError = true
            } else {
                binding.layoutPrice.error = null
            }

            if (selectedCategoryId == -1L) {
                binding.layoutCategory.error = "Category is required"
                hasError = true
            } else {
                binding.layoutCategory.error = null
            }

            if (hasError) {
                com.google.android.material.snackbar.Snackbar.make(binding.root, "Please fill all required fields", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceStr.toDoubleOrNull() ?: 0.0
            val cogs = cogsStr.toDoubleOrNull() ?: 0.0
            val stock = stockStr.toIntOrNull()
            val discountPrice = discountPriceStr.toDoubleOrNull()

            // Save image if selected
            selectedImageUri?.let {
                val path = ImageUtils.saveImageToInternalStorage(requireContext(), it)
                if (path != null) currentImagePath = path
            }

            if (product == null) {
                viewModel.insert(selectedCategoryId, name, price, cogs, stock, currentImagePath, sku, discountPrice, variants)
            } else {
                viewModel.update(
                        product!!,
                        selectedCategoryId,
                        name,
                        price,
                        cogs,
                        stock,
                        currentImagePath,
                        sku,
                        discountPrice
                )
                viewModel.saveVariants(product!!.id, variants)
            }
            dismiss()
        }
    }

    private fun setupVariantUI() {
        product?.let {
            viewModel.getVariantsByProduct(it.id).observe(viewLifecycleOwner) { existingVariants: List<ProductVariant> ->
                variants.clear()
                variants.addAll(existingVariants)
                refreshVariantContainer()
            }
        }

        binding.btnAddVariant.setOnClickListener {
            showAddVariantDialog()
        }
    }

    private fun showAddVariantDialog() {
        val dialogBinding = com.example.pos.databinding.ItemVariantInputBinding.inflate(layoutInflater)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Add Variant")
            .setView(dialogBinding.root)
            .create()

        // Reuse the layout for input by adding EditTexts dynamically or modifying the layout.
        // To keep it simple, I'll use a prompt.
        val nameInput = android.widget.EditText(requireContext()).apply { hint = "Variant Name (e.g. XL, Red)" }
        val priceInput = android.widget.EditText(requireContext()).apply { 
            hint = "Price"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
            addView(nameInput)
            addView(priceInput)
        }

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Add Variant")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString()
                val price = priceInput.text.toString().toDoubleOrNull() ?: 0.0
                if (name.isNotBlank()) {
                    variants.add(ProductVariant(name = name, price = price, productId = product?.id ?: 0, userId = ""))
                    refreshVariantContainer()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun refreshVariantContainer() {
        binding.variantContainer.removeAllViews()
        variants.forEach { variant ->
            val variantBinding = com.example.pos.databinding.ItemVariantInputBinding.inflate(layoutInflater, binding.variantContainer, false)
            variantBinding.variantName.text = variant.name
            variantBinding.variantDetails.text = "Price: ${com.example.pos.utils.CurrencyFormatter.format(variant.price)}"
            variantBinding.btnRemoveVariant.setOnClickListener {
                variants.remove(variant)
                refreshVariantContainer()
            }
            binding.variantContainer.addView(variantBinding.root)
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
