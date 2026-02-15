package com.example.pos.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pos.R
import com.example.pos.data.entity.Product
import com.example.pos.databinding.FragmentProductBinding
import com.example.pos.ui.adapter.ProductAdapter

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeProducts()
    }

    private fun setupUI() {
        productAdapter =
                ProductAdapter(
                        onProductClick = { product -> showEditDialog(product) },
                        onDeleteClick = { product -> showDeleteConfirm(product) }
                )

        binding.productsRecycler.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = productAdapter
        }

        binding.btnAddProduct.setOnClickListener { showEditDialog(null) }
    }

    private fun showEditDialog(product: Product?) {
        ProductEditDialogFragment.newInstance(product).show(childFragmentManager, "edit_product")
    }

    private fun showDeleteConfirm(product: Product) {
        AlertDialog.Builder(requireContext(), R.style.PosDialogStyle)
                .setTitle(R.string.action_delete)
                .setMessage("Are you sure you want to delete product '${product.name}'?")
                .setPositiveButton(R.string.action_delete) { _, _ -> viewModel.delete(product) }
                .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    private fun observeProducts() {
        viewModel.getAllProducts().observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
