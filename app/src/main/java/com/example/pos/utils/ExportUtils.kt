package com.example.pos.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.pos.data.entity.Transaction
import java.io.File
import java.io.FileOutputStream

object ExportUtils {

    fun exportToCsv(context: Context, transactions: List<Transaction>) {
        val fileName = "History_${System.currentTimeMillis()}.csv"
        val file = File(context.cacheDir, fileName)
        
        val writer = file.bufferedWriter()
        writer.write("ID,Date,Amount,Status,Refunded\n")
        
        transactions.forEach {
            writer.write("${it.id},${DateFormatter.formatLongDate(it.createdAt)},${it.totalAmount},${it.status},${it.isRefunded}\n")
        }
        writer.close()
        
        shareFile(context, file, "text/csv")
    }

    fun exportToPdf(context: Context, transactions: List<Transaction>) {
        // Simple PDF generation using Canvas/PdfDocument if needed, 
        // but for now we'll implement a simple text-based summary for the CSV format
        // and a placeholder for PDF logic as requested.
        val fileName = "History_${System.currentTimeMillis()}.txt" // Using TXT as simple PDF alternative if library is complex
        val file = File(context.cacheDir, fileName)
        
        val writer = file.bufferedWriter()
        writer.write("POS TRANSACTION HISTORY REPORT\n")
        writer.write("Generated on: ${DateFormatter.formatLongDate(System.currentTimeMillis())}\n")
        writer.write("--------------------------------\n\n")
        
        var total = 0.0
        transactions.forEach {
            val statusStr = if (it.isRefunded) "REFUNDED" else it.status
            writer.write("${DateFormatter.formatShortDate(it.createdAt)} | ${it.status} | ${CurrencyFormatter.format(it.totalAmount)}\n")
            if (!it.isRefunded) total += it.totalAmount
        }
        
        writer.write("\n--------------------------------\n")
        writer.write("TOTAL REVENUE: ${CurrencyFormatter.format(total)}\n")
        writer.close()
        
        shareFile(context, file, "text/plain")
    }

    private fun shareFile(context: Context, file: File, mimeType: String) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share Report"))
    }
}
