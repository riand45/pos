package com.example.pos.ui.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.data.entity.PaymentMethod
import com.example.pos.databinding.FragmentPaymentBinding
import com.example.pos.ui.adapter.PaymentItemAdapter
import com.example.pos.utils.CurrencyFormatter
import java.text.NumberFormat
import java.util.*

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PaymentDialogFragment : DialogFragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var itemAdapter: PaymentItemAdapter

    private var selectedPaymentMethod = PaymentMethod.CASH
    private var selectedBank: String? = null
    private var totalAmount = 0.0
    private var selectedCustomer: com.example.pos.data.entity.Customer? = null

    companion object {
        private const val ARG_ORDER_ID = "order_id"

        fun newInstance(orderId: Long): PaymentDialogFragment {
            return PaymentDialogFragment().apply {
                arguments = Bundle().apply { putLong(ARG_ORDER_ID, orderId) }
            }
        }
    }

    private lateinit var printerManager: com.example.pos.utils.PrinterManager
    private lateinit var printerPreferences: com.example.pos.utils.PrinterPreferences
    private lateinit var bluetoothAdapter: android.bluetooth.BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        
        // Initialize printer utils
        printerManager = com.example.pos.utils.PrinterManager(requireContext())
        printerPreferences = com.example.pos.utils.PrinterPreferences(requireContext())
        val bluetoothManager = requireContext().getSystemService(android.content.Context.BLUETOOTH_SERVICE) as android.bluetooth.BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupPaymentMethods()
        setupBankSelection()
        setupQuickSelect()
        setupObservers()
        setupCustomerSelection()
    }

    private fun setupUI() {
        itemAdapter = PaymentItemAdapter()
        binding.itemsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }

        binding.btnBack.setOnClickListener { dismiss() }
        binding.btnClose.setOnClickListener { dismiss() }
        binding.btnDiscard.setOnClickListener { dismiss() }

        // Initialize checkbox state from preferences
        binding.checkboxPrint.isChecked = printerPreferences.autoPrint
        
        binding.checkboxPrint.setOnCheckedChangeListener { _, isChecked ->
            printerPreferences.autoPrint = isChecked
        }

        binding.btnComplete.setOnClickListener { completeTransaction() }

        binding.inputAmount.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                    ) {}
                    override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                    ) {}
                    override fun afterTextChanged(s: Editable?) {
                        calculateChange()
                    }
                }
        )
    }

    private fun setupPaymentMethods() {
        binding.btnCash.setOnClickListener { selectPaymentMethod(PaymentMethod.CASH) }

        binding.btnTransfer.setOnClickListener { selectPaymentMethod(PaymentMethod.TRANSFER) }
    }

    private fun selectPaymentMethod(method: PaymentMethod) {
        selectedPaymentMethod = method

        if (method == PaymentMethod.CASH) {
            binding.btnCash.setBackgroundResource(R.drawable.bg_button_success)
            binding.btnCash.setTextColor(resources.getColor(R.color.background_primary, null))
            binding.btnTransfer.setBackgroundResource(R.drawable.bg_button_secondary)
            binding.btnTransfer.setTextColor(resources.getColor(R.color.text_secondary, null))
            binding.quickSelectContainer.visibility = View.VISIBLE
            binding.bankSelectionContainer.visibility = View.GONE
        } else {
            // "color Cash please change color white" -> use secondary/white look
            binding.btnCash.setBackgroundResource(R.drawable.bg_button_secondary)
            binding.btnCash.setTextColor(resources.getColor(R.color.text_secondary, null))
            binding.btnTransfer.setBackgroundResource(R.drawable.bg_button_primary)
            binding.btnTransfer.setTextColor(resources.getColor(R.color.background_primary, null))
            binding.quickSelectContainer.visibility = View.GONE
            binding.bankSelectionContainer.visibility = View.VISIBLE

            // Auto-select first bank if none selected
            if (selectedBank == null) selectBank(getString(R.string.bca))

            // Auto-fill amount for Transfer/QRIS
            val amount = totalAmount.toLong().toString()
            binding.inputAmount.setText(amount)
            binding.inputAmount.setSelection(amount.length)
        }
    }

    private fun setupBankSelection() {
        binding.btnBca.setOnClickListener { selectBank(getString(R.string.bca)) }
        binding.btnQris.setOnClickListener { selectBank(getString(R.string.qris)) }
    }

    private fun selectBank(bank: String) {
        selectedBank = bank
        if (bank == getString(R.string.bca)) {
            binding.btnBca.setBackgroundResource(R.drawable.bg_button_primary)
            binding.btnBca.setTextColor(resources.getColor(R.color.background_primary, null))
            binding.btnQris.setBackgroundResource(R.drawable.bg_button_secondary)
            binding.btnQris.setTextColor(resources.getColor(R.color.text_secondary, null))
        } else {
            binding.btnBca.setBackgroundResource(R.drawable.bg_button_secondary)
            binding.btnBca.setTextColor(resources.getColor(R.color.text_secondary, null))
            binding.btnQris.setBackgroundResource(R.drawable.bg_button_primary)
            binding.btnQris.setTextColor(resources.getColor(R.color.background_primary, null))
        }
    }

    private fun setupQuickSelect() {
        binding.btnUangPas.setOnClickListener {
            val amount = totalAmount.toLong().toString()
            binding.inputAmount.setText(amount)
            binding.inputAmount.setSelection(amount.length)
            updateQuickSelectSelection(it)
        }

        binding.btn20k.setOnClickListener {
            val amount = (((totalAmount / 20000).toInt() + 1) * 20000.0).toLong().toString()
            binding.inputAmount.setText(amount)
            binding.inputAmount.setSelection(amount.length)
            updateQuickSelectSelection(it)
        }

        binding.btn50k.setOnClickListener {
            val amount = (((totalAmount / 50000).toInt() + 1) * 50000.0).toLong().toString()
            binding.inputAmount.setText(amount)
            binding.inputAmount.setSelection(amount.length)
            updateQuickSelectSelection(it)
        }

        binding.btn100k.setOnClickListener {
            val amount = (((totalAmount / 100000).toInt() + 1) * 100000.0).toLong().toString()
            binding.inputAmount.setText(amount)
            binding.inputAmount.setSelection(amount.length)
            updateQuickSelectSelection(it)
        }
    }

    private fun updateQuickSelectSelection(selectedView: View) {
        val buttons = listOf(binding.btnUangPas, binding.btn20k, binding.btn50k, binding.btn100k)
        buttons.forEach { button ->
            if (button == selectedView) {
                button.setBackgroundResource(R.drawable.bg_button_primary)
                button.setTextColor(resources.getColor(R.color.background_primary, null))
            } else {
                button.setBackgroundResource(R.drawable.bg_button_secondary)
                button.setTextColor(resources.getColor(R.color.text_secondary, null))
            }
        }
    }

    private fun setupObservers() {
        viewModel.selectedOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                binding.paymentOrderInfo.text =
                        "Order ${it.orderNumber} • ${it.tableInfo ?: "Dine-In"}"

                totalAmount = it.totalPrice

                binding.subtotalValue.text = CurrencyFormatter.formatShort(totalAmount)
                binding.taxValue.visibility = View.GONE
                binding.totalValue.text = CurrencyFormatter.format(totalAmount)

                // Remove tax label visibility as well
                binding.root.findViewById<View>(R.id.tax_label)?.visibility = View.GONE

                // Auto-fill "Uang Pas" by default for Cash
                if (selectedPaymentMethod == PaymentMethod.CASH &&
                                binding.inputAmount.text.isEmpty()
                ) {
                    val amount = totalAmount.toLong().toString()
                    binding.inputAmount.setText(amount)
                    binding.inputAmount.setSelection(amount.length)
                    updateQuickSelectSelection(binding.btnUangPas)
                }
                
                if (!it.customerName.isNullOrEmpty()) {
                    binding.btnSelectCustomer.text = it.customerName
                    binding.btnSelectCustomer.setIconResource(R.drawable.ic_people)
                }
            }
        }

        viewModel.orderItems.observe(viewLifecycleOwner) { items -> itemAdapter.submitList(items) }
    }

    private fun calculateChange() {
        val amountText = binding.inputAmount.text.toString().replace(",", "").replace(".", "")
        val paidAmount = amountText.toDoubleOrNull() ?: 0.0
        val change = paidAmount - totalAmount

        if (change >= 0) {
            binding.changeValue.text = CurrencyFormatter.formatShort(change)
        } else {
            binding.changeValue.text = "-"
        }
    }

    private fun completeTransaction() {
        val order = viewModel.selectedOrder.value ?: return
        val amountText = binding.inputAmount.text.toString().replace(",", "").replace(".", "")
        val paidAmount = amountText.toDoubleOrNull() ?: 0.0

        if (selectedPaymentMethod == PaymentMethod.CASH && paidAmount < totalAmount) {
            // Show error - insufficient payment
            android.widget.Toast.makeText(requireContext(), "Pembayaran kurang!", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        // Print Receipt if checked
        if (binding.checkboxPrint.isChecked) {
            if (!printerPreferences.hasPrinter()) {
                android.widget.Toast.makeText(requireContext(), "Silakan pilih printer di Pengaturan terlebih dahulu", android.widget.Toast.LENGTH_LONG).show()
                return
            }

            val printerAddress = printerPreferences.printerAddress ?: ""
            if (printerAddress.isNotEmpty()) {
                // Show Progress Dialog
                val progressDialog = android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Mencetak Struk")
                    .setMessage("Mohon tunggu sebentar...")
                    .setCancelable(false)
                    .create()
                progressDialog.show()
                
                // Launch printing coroutine
               lifecycleScope.launch {
                   try {
                       val device = bluetoothAdapter.getRemoteDevice(printerAddress)
                       val connected = printerManager.connect(device)
                       
                       if (connected) {
                           val items = viewModel.orderItems.value ?: emptyList()
                           val receiptItems = items.map { 
                               com.example.pos.utils.PrinterManager.ReceiptItem(
                                   name = it.productName,
                                   price = it.unitPrice,
                                   quantity = it.quantity
                               )
                           }
                           
                           val subtotal = order.totalPrice
                           val tax = 0.0
                           val total = subtotal
                           val change = if (selectedPaymentMethod == PaymentMethod.CASH) paidAmount - total else 0.0
                           
                           printerManager.printReceipt(
                               orderNumber = order.orderNumber,
                               items = receiptItems,
                               subtotal = subtotal,
                               tax = tax,
                               total = total,
                               paymentMethod = selectedPaymentMethod.name,
                               amountPaid = paidAmount,
                               change = change
                           )
                           
                           printerManager.disconnect()
                       } else {
                           android.widget.Toast.makeText(requireContext(), "Gagal terhubung ke printer", android.widget.Toast.LENGTH_SHORT).show()
                       }
                   } catch (e: Exception) {
                       e.printStackTrace()
                       android.widget.Toast.makeText(requireContext(), "Error printer: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                   } finally {
                       progressDialog.dismiss()
                       // Proceed to complete transaction regardless of print success/fail
                       finalizeTransaction(order, paidAmount)
                   }
               }
               return
            }
        }

        finalizeTransaction(order, paidAmount)
    }
    
    private fun setupCustomerSelection() {
        binding.btnSelectCustomer.setOnClickListener {
            val dialog = com.example.pos.ui.customer.CustomerSelectionDialogFragment { customer ->
                selectedCustomer = customer
                binding.btnSelectCustomer.text = customer.name
                binding.btnSelectCustomer.setIconResource(R.drawable.ic_people)
            }
            dialog.show(parentFragmentManager, "CustomerSelection")
        }
    }

    private fun finalizeTransaction(order: com.example.pos.data.entity.Order, paidAmount: Double) {
        val bankName =
                if (selectedPaymentMethod == PaymentMethod.TRANSFER) {
                    selectedBank
                } else null

        viewModel.completeTransaction(
            order = order, 
            paymentMethod = selectedPaymentMethod, 
            amountPaid = paidAmount, 
            bankName = bankName,
            customer = selectedCustomer
        )
        dismiss()
    }

    private fun formatNumber(value: Double): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(value.toLong())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        try {
             printerManager.disconnect()
        } catch (e: Exception) {}
    }
}
