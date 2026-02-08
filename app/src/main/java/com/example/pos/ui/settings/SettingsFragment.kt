package com.example.pos.ui.settings

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.R
import com.example.pos.databinding.FragmentSettingsBinding
import com.example.pos.ui.adapter.BluetoothPrinterAdapter
import com.example.pos.utils.PrinterManager
import com.example.pos.utils.PrinterPreferences
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var printerPreferences: PrinterPreferences
    private lateinit var printerManager: PrinterManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceAdapter: BluetoothPrinterAdapter
    
    private val discoveredDevices = mutableSetOf<BluetoothDevice>()
    
    private val bluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startBluetoothScan()
        } else {
            Toast.makeText(
                requireContext(),
                R.string.bluetooth_permission_required,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                    }
                    
                    val device: BluetoothDevice? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }
                    
                    device?.let {
                        discoveredDevices.add(it)
                        deviceAdapter.addDevice(it)
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    binding.progressScanning.visibility = View.GONE
                    binding.tvScanning.visibility = View.GONE
                }
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        printerPreferences = PrinterPreferences(requireContext())
        printerManager = PrinterManager(requireContext())
        
        val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        
        setupUI()
        loadSavedPrinter()
    }
    
    private fun setupUI() {
        // Setup RecyclerView
        deviceAdapter = BluetoothPrinterAdapter { device ->
            onDeviceSelected(device)
        }
        
        binding.rvBluetoothDevices.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = deviceAdapter
        }
        
        // Setup buttons
        binding.btnScanPrinters.setOnClickListener {
            checkPermissionsAndScan()
        }
        
        binding.btnTestPrint.setOnClickListener {
            testPrint()
        }
    }
    
    private fun loadSavedPrinter() {
        if (printerPreferences.hasPrinter()) {
            binding.tvPrinterName.text = printerPreferences.printerName ?: getString(R.string.no_printer_selected)
            binding.tvPrinterAddress.text = printerPreferences.printerAddress
            binding.tvPrinterAddress.visibility = View.VISIBLE
        } else {
            binding.tvPrinterName.text = getString(R.string.no_printer_selected)
            binding.tvPrinterAddress.visibility = View.GONE
        }
    }
    
    private fun checkPermissionsAndScan() {
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(requireContext(), R.string.enable_bluetooth, Toast.LENGTH_SHORT).show()
            return
        }
        
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        
        val allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
        
        if (allPermissionsGranted) {
            startBluetoothScan()
        } else {
            bluetoothPermissionLauncher.launch(permissions)
        }
    }
    
    private fun startBluetoothScan() {
        try {
            // Show devices list
            binding.layoutDevices.visibility = View.VISIBLE
            binding.progressScanning.visibility = View.VISIBLE
            binding.tvScanning.visibility = View.VISIBLE
            
            // Clear previous results
            discoveredDevices.clear()
            deviceAdapter.clear()
            
            // Get paired devices
            val pairedDevices = try {
                bluetoothAdapter.bondedDevices ?: emptySet()
            } catch (e: SecurityException) {
                emptySet()
            }
            
            // Add paired devices first
            val allDevices = mutableListOf<BluetoothDevice>()
            allDevices.addAll(pairedDevices)
            deviceAdapter.setDevices(allDevices, pairedDevices)
            
            // Register receiver for discovery
            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            requireContext().registerReceiver(bluetoothReceiver, filter)
            
            // Start discovery
            if (bluetoothAdapter.isDiscovering) {
                bluetoothAdapter.cancelDiscovery()
            }
            bluetoothAdapter.startDiscovery()
            
        } catch (e: SecurityException) {
            Toast.makeText(
                requireContext(),
                R.string.bluetooth_permission_required,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    private fun onDeviceSelected(device: BluetoothDevice) {
        try {
            // Stop discovery
            if (bluetoothAdapter.isDiscovering) {
                bluetoothAdapter.cancelDiscovery()
            }
            
            // Save printer
            printerPreferences.printerName = device.name ?: "Unknown Printer"
            printerPreferences.printerAddress = device.address
            
            // Update UI
            loadSavedPrinter()
            
            // Hide devices list
            binding.layoutDevices.visibility = View.GONE
            
            Toast.makeText(
                requireContext(),
                "Printer selected: ${device.name}",
                Toast.LENGTH_SHORT
            ).show()
            
        } catch (e: SecurityException) {
            Toast.makeText(
                requireContext(),
                R.string.bluetooth_permission_required,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    private fun testPrint() {
        val printerAddress = printerPreferences.printerAddress
        
        if (printerAddress.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.select_printer_first, Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            try {
                val device = bluetoothAdapter.getRemoteDevice(printerAddress)
                
                Toast.makeText(requireContext(), "Connecting to printer...", Toast.LENGTH_SHORT).show()
                
                val connected = printerManager.connect(device)
                
                if (connected) {
                    Toast.makeText(requireContext(), R.string.printer_connected, Toast.LENGTH_SHORT).show()
                    
                    val printed = printerManager.printTestReceipt()
                    
                    if (printed) {
                        Toast.makeText(requireContext(), R.string.test_print_success, Toast.LENGTH_SHORT).show()
                        printerPreferences.lastConnectionStatus = true
                    } else {
                        Toast.makeText(requireContext(), R.string.test_print_failed, Toast.LENGTH_SHORT).show()
                        printerPreferences.lastConnectionStatus = false
                    }
                    
                    printerManager.disconnect()
                } else {
                    Toast.makeText(requireContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show()
                    printerPreferences.lastConnectionStatus = false
                }
                
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        
        try {
            if (bluetoothAdapter.isDiscovering) {
                bluetoothAdapter.cancelDiscovery()
            }
            requireContext().unregisterReceiver(bluetoothReceiver)
        } catch (e: Exception) {
            // Receiver not registered or already unregistered
        }
        
        printerManager.disconnect()
        _binding = null
    }
}
