package com.example.pos.ui.order

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.data.entity.Order
import com.example.pos.data.entity.OrderStatus
import com.example.pos.databinding.FragmentOrderKanbanBinding
import com.example.pos.databinding.LayoutKanbanColumnBinding
import com.example.pos.ui.adapter.OrderCardAdapter
import com.example.pos.utils.CurrencyFormatter

class OrderKanbanFragment : Fragment() {

    private var _binding: FragmentOrderKanbanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    private lateinit var queueAdapter: OrderCardAdapter
    private lateinit var processAdapter: OrderCardAdapter
    private lateinit var doneAdapter: OrderCardAdapter
    private lateinit var listAdapter: OrderCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderKanbanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupKanbanColumns()
        setupListView()
        setupNewOrderButton()
        setupSearch()
        setupSort()
        setupViewModeToggle()
        observeOrders()
        observeViewMode()
    }

    private fun setupKanbanColumns() {
        // Queue Column
        val queueBinding = binding.columnQueue
        setupColumn(queueBinding, getString(R.string.status_queue))
        queueAdapter = OrderCardAdapter(
            onCardClick = { order -> onOrderClick(order) },
            onNextClick = { order -> viewModel.updateOrderStatus(order, OrderStatus.PROCESS) },
            onEditClick = { order -> showEditOrderDialog(order) },
            onDeleteClick = { order -> showDeleteConfirmDialog(order) },
            onLongClick = { view, order -> startDrag(view, order) }
        )
        queueBinding.ordersRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = queueAdapter
        }

        // Process Column
        val processBinding = binding.columnProcess
        setupColumn(processBinding, getString(R.string.status_process))
        processBinding.root.setOnDragListener(dragListener) // Accept drops here
        
        processAdapter = OrderCardAdapter(
            onCardClick = { order -> showPaymentDialog(order) },
            onNextClick = { order -> showPaymentDialog(order) },
            onEditClick = { order -> showEditOrderDialog(order) },
            onDeleteClick = { order -> showDeleteConfirmDialog(order) },
            showProgress = true
        )
        processBinding.ordersRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = processAdapter
        }

        // Done Column
        val doneBinding = binding.columnDone
        setupColumn(doneBinding, getString(R.string.status_done))
        doneAdapter = OrderCardAdapter(
            onCardClick = { order -> onOrderClick(order) },
            onNextClick = { },
            onArchiveClick = { order -> viewModel.archiveOrder(order) },
            showProgress = false,
            showNextButton = true
        )
        doneBinding.ordersRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = doneAdapter
        }
    }

    private fun setupListView() {
        listAdapter = OrderCardAdapter(
            onCardClick = { order -> 
                if (order.status == OrderStatus.PROCESS) showPaymentDialog(order)
                else onOrderClick(order)
            },
            onNextClick = { order -> 
                if (order.status == OrderStatus.QUEUE) viewModel.updateOrderStatus(order, OrderStatus.PROCESS)
                else if (order.status == OrderStatus.PROCESS) showPaymentDialog(order)
            },
            onEditClick = { order -> showEditOrderDialog(order) },
            onDeleteClick = { order -> showDeleteConfirmDialog(order) },
            onArchiveClick = { order -> viewModel.archiveOrder(order) },
            showProgress = true, // Show progress bar if applicable (handled by adapter based on status?? Adapter handles visibility based on status, showProgress just enables the space)
            // Actually OrderCardAdapter logic for showProgress is: `visibility = if (showProgress) View.VISIBLE else View.GONE`. 
            // Better to let adapter determine based on status if I want dynamic behavior, but adapter logic binds it to `showProgress` flag AND status specific visibility? 
            // In adapter: `binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE`.
            // So if I set showProgress=true, it shows for ALL items in list adapter. 
            // Ideally, only PROCESS items show progress. 
            // Recommendation: Layout item_order_card already has logic *inside* onBind? No, it uses `showProgress` param.
            // I'll leave it as false for list view to avoid clutter, or true if requested.
            // Let's check adapter code: `binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE`.
            // It doesn't check status for progress bar visibility, just the flag.
            // I'll set it to false for now for cleaner list view, or true if we want to see it.
            showNextButton = true
        )
        binding.listViewRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun setupColumn(columnBinding: LayoutKanbanColumnBinding, title: String) {
        columnBinding.columnTitle.text = title
        columnBinding.columnCount.text = "0"
    }

    private fun setupNewOrderButton() {
        binding.btnNewOrder.setOnClickListener {
            showNewOrderDialog()
        }
    }

    private fun setupSearch() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSort() {
        binding.btnSort.setOnClickListener {
            viewModel.toggleSortOrder()
        }
    }

    private fun setupViewModeToggle() {
        binding.btnKanbanView.setOnClickListener {
            viewModel.setViewMode(OrderViewModel.ViewMode.KANBAN)
        }
        binding.btnListView.setOnClickListener {
            viewModel.setViewMode(OrderViewModel.ViewMode.LIST)
        }
    }

    private fun observeOrders() {
        viewModel.queueOrders.observe(viewLifecycleOwner) { orders ->
            queueAdapter.submitList(orders)
            binding.columnQueue.columnCount.text = orders.size.toString()
        }

        viewModel.processOrders.observe(viewLifecycleOwner) { orders ->
            processAdapter.submitList(orders)
            binding.columnProcess.columnCount.text = orders.size.toString()
        }

        viewModel.doneOrders.observe(viewLifecycleOwner) { orders ->
            doneAdapter.submitList(orders)
            binding.columnDone.columnCount.text = orders.size.toString()
        }

        viewModel.allOrders.observe(viewLifecycleOwner) { orders ->
            listAdapter.submitList(orders)
        }
    }

    private fun observeViewMode() {
        viewModel.viewMode.observe(viewLifecycleOwner) { mode ->
            if (mode == OrderViewModel.ViewMode.KANBAN) {
                binding.kanbanScrollView.visibility = View.VISIBLE
                binding.listViewRecycler.visibility = View.GONE
                updateViewModeButtons(isKanban = true)
            } else {
                binding.kanbanScrollView.visibility = View.GONE
                binding.listViewRecycler.visibility = View.VISIBLE
                updateViewModeButtons(isKanban = false)
            }
        }

        viewModel.isNewestFirst.observe(viewLifecycleOwner) { isNewest ->
             binding.sortLabel.text = if (isNewest) getString(R.string.newest_first) else getString(R.string.oldest_first)
        }
    }

    private fun updateViewModeButtons(isKanban: Boolean) {
        val primaryBg = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_primary)
        val transparentBg = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.background_primary)
        val secondaryColor = ContextCompat.getColor(requireContext(), R.color.text_secondary)

        if (isKanban) {
            binding.btnKanbanView.background = primaryBg
            binding.btnKanbanView.setTextColor(primaryColor)
            binding.btnListView.background = transparentBg
            binding.btnListView.setTextColor(secondaryColor)
        } else {
            binding.btnKanbanView.background = transparentBg
            binding.btnKanbanView.setTextColor(secondaryColor)
            binding.btnListView.background = primaryBg
            binding.btnListView.setTextColor(primaryColor)
        }
    }

    private fun onOrderClick(order: Order) {
        viewModel.selectOrder(order)
        // detailed view?
    }

    private fun showPaymentDialog(order: Order) {
        viewModel.selectOrder(order)
        val dialog = PaymentDialogFragment.newInstance(order.id)
        dialog.show(childFragmentManager, "payment_dialog")
    }

    private fun showNewOrderDialog() {
        val dialog = NewOrderDialogFragment()
        dialog.show(childFragmentManager, "new_order_dialog")
    }

    private fun showEditOrderDialog(order: Order) {
        viewModel.selectOrder(order)
        NewOrderDialogFragment().show(childFragmentManager, "edit_order")
    }

    private fun showDeleteConfirmDialog(order: Order) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.PosDialogStyle)
            .setTitle("Delete Order")
            .setMessage("Are you sure you want to delete order ${order.orderNumber}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteOrder(order)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Drag and Drop Logic
    private fun startDrag(view: View, order: Order): Boolean {
        val item = ClipData.Item(order.id.toString())
        val dragData = ClipData(
            order.id.toString(),
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )
        
        val shadow = View.DragShadowBuilder(view)
        
        view.startDragAndDrop(dragData, shadow, order, 0)
        return true
    }

    private val dragListener = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.alpha = 0.7f // Highlight drop target
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                view.alpha = 1.0f
                true
            }
            DragEvent.ACTION_DROP -> {
                view.alpha = 1.0f
                val order = event.localState as? Order
                if (order != null && order.status == OrderStatus.QUEUE) {
                    viewModel.updateOrderStatus(order, OrderStatus.PROCESS)
                    true
                } else {
                    false
                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.alpha = 1.0f
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
