package com.example.pos.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.data.entity.Transaction
import com.example.pos.databinding.DialogTransactionDetailBinding
import com.example.pos.ui.adapter.PaymentItemAdapter
import com.example.pos.utils.CurrencyFormatter
import com.example.pos.utils.DateFormatter

class TransactionDetailDialogFragment : DialogFragment() {

    private var _binding: DialogTransactionDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private var transaction: Transaction? = null

    companion object {
        fun newInstance(transaction: Transaction): TransactionDetailDialogFragment {
            val fragment = TransactionDetailDialogFragment()
            fragment.transaction = transaction
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
        _binding = DialogTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        transaction?.let { tx ->
            binding.orderNumber.text = tx.orderNumber
            binding.transactionTime.text = DateFormatter.formatLongDate(tx.createdAt)
            binding.totalAmount.text = CurrencyFormatter.format(tx.totalAmount)

            binding.statusBadge.text = if (tx.isRefunded) "REFUNDED" else "SUCCESS"
            binding.statusBadge.setBackgroundResource(
                if (tx.isRefunded) R.drawable.bg_badge_warning else R.drawable.bg_button_success
            )

            binding.btnRefund.visibility = if (tx.isRefunded) View.GONE else View.VISIBLE
            binding.btnRefund.setOnClickListener {
                viewModel.refundTransaction(tx)
                dismiss()
            }

            loadOrderItems(tx.orderId)
        }

        binding.btnClose.setOnClickListener { dismiss() }
    }

    private fun loadOrderItems(orderId: Long) {
        val app = requireActivity().application as PosApplication
        app.orderRepository.getOrderWithItemsById(orderId).observe(viewLifecycleOwner) { orderWithItems ->
            orderWithItems?.let {
                val adapter = PaymentItemAdapter()
                binding.itemsRecycler.adapter = adapter
                adapter.submitList(it.items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
