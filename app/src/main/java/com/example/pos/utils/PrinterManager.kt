package com.example.pos.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.util.*

class PrinterManager(private val context: Context) {
    
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    
    companion object {
        // Standard SerialPortService ID for Bluetooth SPP
        private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        
        // ESC/POS Commands
        private val ESC: Byte = 0x1B
        private val GS: Byte = 0x1D
        
        // Initialize printer
        private val CMD_INIT = byteArrayOf(ESC, '@'.code.toByte())
        
        // Text alignment
        private val CMD_ALIGN_LEFT = byteArrayOf(ESC, 'a'.code.toByte(), 0)
        private val CMD_ALIGN_CENTER = byteArrayOf(ESC, 'a'.code.toByte(), 1)
        
        // Text size
        private val CMD_NORMAL_TEXT = byteArrayOf(ESC, '!'.code.toByte(), 0)
        private val CMD_LARGE_TEXT = byteArrayOf(ESC, '!'.code.toByte(), 0x30.toByte())
        
        // Feed and cut
        private val CMD_FEED_LINE = byteArrayOf(ESC, 'd'.code.toByte(), 1)
        private val CMD_FEED_PAPER = byteArrayOf(ESC, 'd'.code.toByte(), 3)
        private val CMD_CUT_PAPER = byteArrayOf(GS, 'V'.code.toByte(), 1)
        
        private const val LINE_SEPARATOR = "--------------------------------"
    }
    
    suspend fun connect(device: BluetoothDevice): Boolean = withContext(Dispatchers.IO) {
        try {
            disconnect()
            
            bluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            bluetoothSocket?.connect()
            outputStream = bluetoothSocket?.outputStream
            
            // Initialize printer
            outputStream?.write(CMD_INIT)
            outputStream?.flush()
            
            true
        } catch (e: IOException) {
            e.printStackTrace()
            disconnect()
            false
        }
    }
    
    fun disconnect() {
        try {
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream = null
            bluetoothSocket = null
        }
    }
    
    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected == true
    }
    
    suspend fun printTestReceipt(): Boolean = withContext(Dispatchers.IO) {
        try {
            if (!isConnected()) {
                return@withContext false
            }
            
            val output = outputStream ?: return@withContext false
            
            // Initialize
            output.write(CMD_INIT)
            
            // Header - Large centered text
            output.write(CMD_ALIGN_CENTER)
            output.write(CMD_LARGE_TEXT)
            output.write("POS SYSTEM\n".toByteArray())
            
            // Subtitle
            output.write(CMD_NORMAL_TEXT)
            output.write("Test Receipt\n".toByteArray())
            output.write(CMD_FEED_LINE)
            
            // Separator
            output.write(CMD_ALIGN_LEFT)
            output.write("$LINE_SEPARATOR\n".toByteArray())
            
            // Test content
            output.write("Printer Test Successful!\n".toByteArray())
            output.write("Date: ${getCurrentDateTime()}\n".toByteArray())
            output.write("$LINE_SEPARATOR\n".toByteArray())
            
            // Status
            output.write(CMD_ALIGN_CENTER)
            output.write("\nConnection: OK\n".toByteArray())
            output.write("Print Quality: OK\n".toByteArray())
            
            // Footer
            output.write(CMD_FEED_LINE)
            output.write("Thank you!\n".toByteArray())
            
            // Feed and cut
            output.write(CMD_FEED_PAPER)
            output.write(CMD_CUT_PAPER)
            
            output.flush()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
    
    suspend fun printReceipt(
        orderNumber: String,
        items: List<ReceiptItem>,
        subtotal: Double,
        tax: Double,
        total: Double,
        paymentMethod: String,
        amountPaid: Double,
        change: Double
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            if (!isConnected()) {
                return@withContext false
            }
            
            val output = outputStream ?: return@withContext false
            
            // Initialize
            output.write(CMD_INIT)
            
            // Header
            output.write(CMD_ALIGN_CENTER)
            output.write(CMD_LARGE_TEXT)
            output.write("POS SYSTEM\n".toByteArray())
            output.write(CMD_NORMAL_TEXT)
            output.write("Receipt\n".toByteArray())
            output.write(CMD_FEED_LINE)
            
            // Order info
            output.write(CMD_ALIGN_LEFT)
            output.write("Order #: $orderNumber\n".toByteArray())
            output.write("Date: ${getCurrentDateTime()}\n".toByteArray())
            output.write("$LINE_SEPARATOR\n".toByteArray())
            
            // Items
            for (item in items) {
                val line = String.format("%-20s %6s\n", 
                    truncate(item.name, 20),
                    formatCurrency(item.price * item.quantity))
                output.write(line.toByteArray())
                
                if (item.quantity > 1) {
                    val qtyLine = String.format("  %d x %s\n",
                        item.quantity,
                        formatCurrency(item.price))
                    output.write(qtyLine.toByteArray())
                }
            }
            
            output.write("$LINE_SEPARATOR\n".toByteArray())
            
            // Totals
            output.write(String.format("%-20s %6s\n", "Subtotal:", formatCurrency(subtotal)).toByteArray())
            output.write(String.format("%-20s %6s\n", "Tax:", formatCurrency(tax)).toByteArray())
            output.write(CMD_LARGE_TEXT)
            output.write(String.format("%-20s %6s\n", "TOTAL:", formatCurrency(total)).toByteArray())
            output.write(CMD_NORMAL_TEXT)
            
            output.write("$LINE_SEPARATOR\n".toByteArray())
            
            // Payment
            output.write("Payment: $paymentMethod\n".toByteArray())
            output.write(String.format("%-20s %6s\n", "Paid:", formatCurrency(amountPaid)).toByteArray())
            output.write(String.format("%-20s %6s\n", "Change:", formatCurrency(change)).toByteArray())
            
            // Footer
            output.write(CMD_FEED_LINE)
            output.write(CMD_ALIGN_CENTER)
            output.write("Thank you for your purchase!\n".toByteArray())
            
            // Feed and cut
            output.write(CMD_FEED_PAPER)
            output.write(CMD_CUT_PAPER)
            
            output.flush()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
    
    private fun getCurrentDateTime(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
    
    private fun formatCurrency(amount: Double): String {
        return String.format("Rp %,.0f", amount)
    }
    
    private fun truncate(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength - 3) + "..."
        } else {
            text
        }
    }
    
    data class ReceiptItem(
        val name: String,
        val price: Double,
        val quantity: Int
    )
}
