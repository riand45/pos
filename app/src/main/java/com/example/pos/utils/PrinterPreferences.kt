package com.example.pos.utils

import android.content.Context
import android.content.SharedPreferences

class PrinterPreferences(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "printer_preferences"
        private const val KEY_PRINTER_NAME = "printer_name"
        private const val KEY_PRINTER_ADDRESS = "printer_address"
        private const val KEY_AUTO_PRINT = "auto_print"
        private const val KEY_LAST_CONNECTION_STATUS = "last_connection_status"
    }
    
    var printerName: String?
        get() = prefs.getString(KEY_PRINTER_NAME, null)
        set(value) = prefs.edit().putString(KEY_PRINTER_NAME, value).apply()
    
    var printerAddress: String?
        get() = prefs.getString(KEY_PRINTER_ADDRESS, null)
        set(value) = prefs.edit().putString(KEY_PRINTER_ADDRESS, value).apply()
    
    var autoPrint: Boolean
        get() = prefs.getBoolean(KEY_AUTO_PRINT, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_PRINT, value).apply()
    
    var lastConnectionStatus: Boolean
        get() = prefs.getBoolean(KEY_LAST_CONNECTION_STATUS, false)
        set(value) = prefs.edit().putBoolean(KEY_LAST_CONNECTION_STATUS, value).apply()
    
    fun clearPrinter() {
        prefs.edit()
            .remove(KEY_PRINTER_NAME)
            .remove(KEY_PRINTER_ADDRESS)
            .remove(KEY_LAST_CONNECTION_STATUS)
            .apply()
    }
    
    fun hasPrinter(): Boolean {
        return !printerAddress.isNullOrEmpty()
    }
}
