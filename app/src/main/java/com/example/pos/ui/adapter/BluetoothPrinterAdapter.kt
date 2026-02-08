package com.example.pos.ui.adapter

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R

class BluetoothPrinterAdapter(
    private val onDeviceClick: (BluetoothDevice) -> Unit
) : RecyclerView.Adapter<BluetoothPrinterAdapter.DeviceViewHolder>() {
    
    private val devices = mutableListOf<BluetoothDevice>()
    private val pairedDevices = mutableSetOf<String>()
    
    fun setDevices(newDevices: List<BluetoothDevice>, paired: Set<BluetoothDevice>) {
        devices.clear()
        devices.addAll(newDevices)
        pairedDevices.clear()
        pairedDevices.addAll(paired.map { it.address })
        notifyDataSetChanged()
    }
    
    fun addDevice(device: BluetoothDevice) {
        if (!devices.any { it.address == device.address }) {
            devices.add(device)
            notifyItemInserted(devices.size - 1)
        }
    }
    
    fun clear() {
        devices.clear()
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bluetooth_device, parent, false)
        return DeviceViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }
    
    override fun getItemCount(): Int = devices.size
    
    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDeviceName: TextView = itemView.findViewById(R.id.tv_device_name)
        private val tvDeviceAddress: TextView = itemView.findViewById(R.id.tv_device_address)
        private val tvDeviceStatus: TextView = itemView.findViewById(R.id.tv_device_status)
        
        fun bind(device: BluetoothDevice) {
            try {
                tvDeviceName.text = device.name ?: "Unknown Device"
            } catch (e: SecurityException) {
                tvDeviceName.text = "Unknown Device"
            }
            
            tvDeviceAddress.text = device.address
            
            val isPaired = pairedDevices.contains(device.address)
            if (isPaired) {
                tvDeviceStatus.text = itemView.context.getString(R.string.paired)
                tvDeviceStatus.visibility = View.VISIBLE
            } else {
                tvDeviceStatus.text = itemView.context.getString(R.string.not_paired)
                tvDeviceStatus.visibility = View.VISIBLE
            }
            
            itemView.setOnClickListener {
                onDeviceClick(device)
            }
        }
    }
}
