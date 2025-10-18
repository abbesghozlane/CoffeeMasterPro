package com.coffeemaster.app.services.export

import android.content.Context
import android.net.Uri
import com.coffeemaster.app.models.Report
import com.coffeemaster.app.data.Repository
import com.coffeemaster.app.data.entity.ReportEntity
import java.util.Date
import com.coffeemaster.app.models.ExportDestination
import com.coffeemaster.app.models.ExportFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Intent
import android.provider.Settings
import androidx.core.content.FileProvider

class ExportManager(private val context: Context) {

    suspend fun exportReport(report: Report, format: ExportFormat, destination: ExportDestination): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            when (format) {
                ExportFormat.PDF -> exportToPdf(report, destination)
                ExportFormat.DOCX -> exportToDocx(report, destination)
                ExportFormat.EXCEL -> Result.failure(Exception("Excel export not implemented"))
                ExportFormat.HTML -> Result.failure(Exception("HTML export not implemented"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun exportToPdf(report: Report, destination: ExportDestination): Result<Uri> {
        return try {
            val pdfContent = PdfGenerator.generateReportPdf(report)
            when (destination) {
                ExportDestination.DEVICE -> saveToDevice(pdfContent, "${report.id}.pdf", "application/pdf")
                ExportDestination.COMPUTER -> {
                    val saved = saveToDevice(pdfContent, "${report.id}.pdf", "application/pdf")
                    if (saved.isSuccess) {
                        val uri = saved.getOrNull()
                        uri?.path?.let { path ->
                            val f = java.io.File(path)
                            return@let shareToComputer(f, "application/pdf")
                        }
                    }
                    Result.failure(Exception("Failed to save before sharing"))
                }
                ExportDestination.CLOUD -> saveToCloud(pdfContent, "${report.id}.pdf")
                ExportDestination.PRINT -> printDocument(pdfContent, "${report.id}.pdf", "application/pdf")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun exportToDocx(report: Report, destination: ExportDestination): Result<Uri> {
        return try {
            val docxContent = DocxGenerator.generateReportDocx(report)
            when (destination) {
                ExportDestination.DEVICE -> saveToDevice(docxContent, "${report.id}.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                ExportDestination.COMPUTER -> {
                    val saved = saveToDevice(docxContent, "${report.id}.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    if (saved.isSuccess) {
                        val uri = saved.getOrNull()
                        uri?.path?.let { path ->
                            val f = java.io.File(path)
                            return@let shareToComputer(f, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        }
                    }
                    Result.failure(Exception("Failed to save before sharing"))
                }
                ExportDestination.CLOUD -> saveToCloud(docxContent, "${report.id}.docx")
                ExportDestination.PRINT -> printDocument(docxContent, "${report.id}.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveToDevice(content: ByteArray, fileName: String, mimeType: String): Result<Uri> {
        // Minimal implementation: write to app files dir and return Uri
        return try {
            val file = java.io.File(context.filesDir, fileName)
            file.parentFile?.mkdirs()
            file.writeBytes(content)
            // persist a report record
            try {
                val repo = Repository.getInstance(context)
                val entity = ReportEntity(
                    id = fileName,
                    type = file.extension.uppercase(),
                    period = Date().toString(),
                    payloadJson = "",
                    generatedAt = Date().time,
                    generatedBy = "system"
                )
                repo.reportDao.insert(entity)
            } catch (_: Exception) {
                // ignore DB errors for now
            }
            Result.success(Uri.fromFile(file))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun shareToComputer(file: java.io.File, mimeType: String) : Result<Uri> {
        return try {
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = mimeType
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(Intent.createChooser(intent, "Share Report"))
            Result.success(uri)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveToCloud(content: ByteArray, fileName: String): Result<Uri> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Stub: save to cache and return URI. Real implementation should upload to Drive/Dropbox and return cloud URI
            val file = saveToTempFile(content, fileName)
            Result.success(Uri.fromFile(file))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveToTempFile(content: ByteArray, fileName: String): java.io.File {
        val file = java.io.File(context.cacheDir, fileName)
        file.parentFile?.mkdirs()
        file.writeBytes(content)
        return file
    }

    private fun printDocument(content: ByteArray, fileName: String, mimeType: String): Result<Uri> {
        return try {
            val file = saveToTempFile(content, fileName)
            // Use PrintManager to print the file
            val printManager = context.getSystemService(android.content.Context.PRINT_SERVICE) as android.print.PrintManager
            val jobName = "CoffeeMaster - $fileName"
            val adapter = object : android.print.PrintDocumentAdapter() {
                override fun onLayout(oldAttributes: android.print.PrintAttributes?, newAttributes: android.print.PrintAttributes?, cancellationSignal: android.os.CancellationSignal?, callback: android.print.PrintDocumentAdapter.LayoutResultCallback?, extras: android.os.Bundle?) {
                    if (cancellationSignal?.isCanceled == true) {
                        callback?.onLayoutCancelled()
                        return
                    }
                    val pdi = android.print.PrintDocumentInfo.Builder(file.name).setContentType(android.print.PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).setPageCount(android.print.PrintDocumentInfo.PAGE_COUNT_UNKNOWN).build()
                    callback?.onLayoutFinished(pdi, true)
                }

                override fun onWrite(pages: Array<android.print.PageRange>?, destination: android.os.ParcelFileDescriptor?, cancellationSignal: android.os.CancellationSignal?, callback: android.print.PrintDocumentAdapter.WriteResultCallback?) {
                    try {
                        destination?.fileDescriptor?.let { fd ->
                            java.io.FileInputStream(file).use { input ->
                                java.io.FileOutputStream(fd).use { output ->
                                    input.copyTo(output)
                                }
                            }
                        }
                        callback?.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
                    } catch (e: Exception) {
                        callback?.onWriteFailed(e.message)
                    }
                }
            }

            // start print job on the main thread
            android.os.Handler(context.mainLooper).post {
                printManager.print(jobName, adapter, null)
            }

            Result.success(Uri.fromFile(file))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = android.net.Uri.parse("package:${context.packageName}")
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
