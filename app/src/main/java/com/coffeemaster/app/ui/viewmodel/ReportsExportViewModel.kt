package com.coffeemaster.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeemaster.app.models.ExportDestination
import com.coffeemaster.app.models.ExportFormat
import com.coffeemaster.app.models.Report
import com.coffeemaster.app.services.export.ExportManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportsExportViewModel(private val context: Context) : ViewModel() {
    private val _inProgress = MutableStateFlow(false)
    val inProgress: StateFlow<Boolean> = _inProgress

    fun export(report: Report, format: ExportFormat, destination: ExportDestination, callback: (Result) -> Unit) {
        viewModelScope.launch {
            _inProgress.value = true
            val manager = ExportManager(context)
            val res = manager.exportReport(report, format, destination)
            _inProgress.value = false
            if (res.isSuccess) {
                val uri = res.getOrNull()
                callback(Result.Success(uri?.toString()))
            } else {
                val ex = res.exceptionOrNull()
                callback(Result.Failure(ex?.message ?: "Unknown error"))
            }
        }
    }

    sealed class Result {
        data class Success(val uriString: String?) : Result()
        data class Failure(val message: String) : Result()
    }
}
