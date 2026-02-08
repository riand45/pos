package com.example.pos.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.databinding.DialogNewOrderBinding
import com.example.pos.ui.adapter.ProductSelectAdapter
import io.github.jan.supabase.gotrue.auth

class NewOrderDialogFragment : DialogFragment() {

    private var _binding: DialogNewOrderBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var productAdapter: ProductSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = DialogNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadProducts()
    }

    private fun setupUI() {
        productAdapter = ProductSelectAdapter { product ->
            viewModel.addToCart(product)
            updateCartSummary()
        }

        binding.productsRecycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = productAdapter
        }

        binding.btnClose.setOnClickListener {
            viewModel.clearCart()
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            val tableInfo = binding.inputTable.text.toString().ifEmpty { null }
            viewModel.createOrder(tableInfo)
            dismiss()
        }

        updateCartSummary()
    }

    private fun loadProducts() {
        val app = requireActivity().application as PosApplication
        val userId = app.supabase.auth.currentUserOrNull()?.id ?: ""
        app.productRepository.getAllProducts(userId).observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
        }
    }

    private fun updateCartSummary() {
        val cart = viewModel.cartItems.value ?: emptyList()
        val itemCount = cart.sumOf { it.quantity }
        binding.cartCount.text = "$itemCount items"
        binding.btnConfirm.isEnabled = itemCount > 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
