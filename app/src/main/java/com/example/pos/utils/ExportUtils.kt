package com.example.pos.utils

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.pos.data.entity.Transaction
import java.io.File
import java.io.FileOutputStream

object ExportUtils {

    fun exportToCsv(context: Context, transactions: List<Transaction>) {
        val fileName = "History_${System.currentTimeMillis()}.csv"
        val file = File(context.cacheDir, fileName)
        
        val writer = file.bufferedWriter()
        // Header
        writer.write("Order Number,Date,Customer Name,Payment Method,Bank Name,Subtotal,Tax,Total Amount,Total COGS,Amount Paid,Change Amount,Status,Refunded\n")
        
        transactions.forEach {
            val row = listOf(
                it.orderNumber,
                DateFormatter.formatLongDate(it.createdAt),
                it.customerName ?: "-",
                it.paymentMethod.name,
                it.bankName ?: "-",
                it.subtotal.toString(),
                it.tax.toString(),
                it.totalAmount.toString(),
                it.totalCogs.toString(),
                it.amountPaid.toString(),
                it.changeAmount.toString(),
                it.status,
                it.isRefunded.toString()
            ).joinToString(",") { field -> 
                // Escape commas in fields if any
                if (field.contains(",")) "\"$field\"" else field 
            }
            writer.write("$row\n")
        }
        writer.close()
        
        shareFile(context, file, "text/csv")
    }

    fun exportToPdf(context: Context, transactions: List<Transaction>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Title
        paint.color = Color.BLACK
        paint.textSize = 20f
        paint.isFakeBoldText = true
        canvas.drawText("POS TRANSACTION HISTORY REPORT", 50f, 50f, paint)

        // Metadata
        paint.textSize = 12f
        paint.isFakeBoldText = false
        canvas.drawText("Generated on: ${DateFormatter.formatLongDate(System.currentTimeMillis())}", 50f, 80f, paint)
        canvas.drawText("Total Transactions: ${transactions.size}", 50f, 100f, paint)

        // Table Header
        paint.isFakeBoldText = true
        var y = 140f
        canvas.drawText("Date", 50f, y, paint)
        canvas.drawText("Status", 150f, y, paint)
        canvas.drawText("Amount", 450f, y, paint)
        
        canvas.drawLine(50f, y + 5, 550f, y + 5, paint)
        y += 30f

        // Table Body
        paint.isFakeBoldText = false
        var total = 0.0
        transactions.forEach {
            canvas.drawText(DateFormatter.formatShortDate(it.createdAt), 50f, y, paint)
            canvas.drawText(if (it.isRefunded) "Refunded" else it.status, 150f, y, paint)
            canvas.drawText(CurrencyFormatter.format(it.totalAmount), 450f, y, paint)
            
            if (!it.isRefunded) total += it.totalAmount
            y += 20f
            
            // Basic page break logic could go here if y > 800
        }

        // Total
        y += 20f
        paint.isFakeBoldText = true
        canvas.drawLine(50f, y - 15, 550f, y - 15, paint)
        canvas.drawText("TOTAL REVENUE:", 300f, y, paint)
        canvas.drawText(CurrencyFormatter.format(total), 450f, y, paint)

        pdfDocument.finishPage(page)

        val fileName = "History_${System.currentTimeMillis()}.pdf"
        val file = File(context.cacheDir, fileName)
        
        try {
            pdfDocument.writeTo(FileOutputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pdfDocument.close()
        
        shareFile(context, file, "application/pdf")
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
