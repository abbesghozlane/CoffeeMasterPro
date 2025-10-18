package com.coffeemaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.platform.LocalContext
import com.coffeemaster.app.models.Report
import com.coffeemaster.app.models.ReportData
import com.coffeemaster.app.models.ReportSummary
import com.coffeemaster.app.ui.viewmodel.ReportsExportViewModel
import com.coffeemaster.app.models.*
import com.coffeemaster.app.services.PermissionsManager
import com.coffeemaster.app.ui.components.Button3D
import com.coffeemaster.app.ui.theme.AppThemeColors

@Composable
fun ReportsScreen(onBack: () -> Unit, currentUser: User) {
    var selectedPeriod by remember { mutableStateOf(ReportPeriod.LAST_7_DAYS) }
    var selectedReportType by remember { mutableStateOf(ReportType.DAILY) }
    var showExportDialog by remember { mutableStateOf(false) }
    var exportInProgress by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = scaffoldState.snackbarHostState
    val ctx = LocalContext.current
    val exportVm = remember { ReportsExportViewModel(ctx) }

    val viewModel: ReportsViewModel = viewModel()
    val reportData by viewModel.reportData.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadSavedReports(context)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("التقارير المالية") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (PermissionsManager.canExportReports(currentUser)) {
                        IconButton(onClick = { showExportDialog = true }, enabled = !exportInProgress) {
                            if (exportInProgress) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = AppThemeColors.textWhite, strokeWidth = 2.dp)
                            } else {
                                Icon(imageVector = Icons.Default.Download, contentDescription = "Export Report")
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(AppThemeColors.backgroundCream)) {
            // Placeholder content
            Text("Report Summary Placeholder", modifier = Modifier.padding(16.dp))
        }

        if (showExportDialog) {
            ExportDialog(onDismiss = { showExportDialog = false }, onExport = { format, destination ->
                exportInProgress = true
                showExportDialog = false

                // create a sample Report to export
                val sampleReport = Report(
                    id = "report_${System.currentTimeMillis()}",
                    type = ReportType.DAILY,
                    period = ReportPeriod.TODAY,
                    dateRange = Date()..Date(),
                    data = ReportData(ReportSummary(0.0,0.0,0.0,0.0, "", null), SalesData(), ExpensesData(), InventoryData(), null, emptyList()),
                    generatedBy = currentUser.id
                )

                // call export VM
                exportVm.export(sampleReport, format, destination) { result ->
                    exportInProgress = false
                    when (result) {
                        is com.coffeemaster.app.ui.viewmodel.ReportsExportViewModel.Result.Success -> {
                            scaffoldState.snackbarHostState.showSnackbar("Exported: ${result.uriString}")
                        }
                        is com.coffeemaster.app.ui.viewmodel.ReportsExportViewModel.Result.Failure -> {
                            scaffoldState.snackbarHostState.showSnackbar("Export failed: ${result.message}")
                        }
                    }
                }
            })
        }
    }
}

@Composable
fun ExportDialog(onDismiss: () -> Unit, onExport: (ExportFormat, ExportDestination) -> Unit) {
    var selectedFormat by remember { mutableStateOf(ExportFormat.PDF) }
    var selectedDestination by remember { mutableStateOf(ExportDestination.DEVICE) }

    AlertDialog(onDismissRequest = onDismiss, title = { Text("تصدير التقرير") }, text = {
        Column {
            Text("اختر صيغة التصدير:", style = MaterialTheme.typography.subtitle1)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ExportFormat.values().forEach { format ->
                    FormatChip(format = format, isSelected = selectedFormat == format, onClick = { selectedFormat = format })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("وجهة التصدير:", style = MaterialTheme.typography.subtitle1)
            Column {
                ExportDestination.values().forEach { destination ->
                    DestinationRadioButton(destination = destination, isSelected = selectedDestination == destination, onClick = { selectedDestination = destination })
                }
            }
        }
    }, confirmButton = {
        Button3D(onClick = { onExport(selectedFormat, selectedDestination) }, text = "تصدير", icon = Icons.Default.Download)
    }, dismissButton = {
        TextButton(onClick = onDismiss) { Text("إلغاء") }
    })
}

@Composable
fun FormatChip(format: ExportFormat, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) AppThemeColors.primaryBrown else androidx.compose.ui.graphics.Color.LightGray
    val contentColor = if (isSelected) AppThemeColors.textWhite else AppThemeColors.textPrimary

    Card(modifier = Modifier.width(80.dp).height(40.dp).clickable { onClick() }, backgroundColor = backgroundColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = when (format) {
                ExportFormat.PDF -> "PDF"
                ExportFormat.DOCX -> "Word"
                ExportFormat.EXCEL -> "Excel"
                ExportFormat.HTML -> "HTML"
            }, color = contentColor, fontSize = 12.sp)
        }
    }
}

@Composable
fun DestinationRadioButton(destination: ExportDestination, isSelected: Boolean, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = onClick, colors = RadioButtonDefaults.colors(selectedColor = AppThemeColors.primaryBrown))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = when (destination) {
                ExportDestination.DEVICE -> "حفظ في الهاتف"
                ExportDestination.COMPUTER -> "مشاركة إلى الكمبيوتر"
                ExportDestination.CLOUD -> "رفع إلى السحابة"
                ExportDestination.PRINT -> "طباعة مباشرة"
            })
            Text(text = when (destination) {
                ExportDestination.DEVICE -> "حفظ الملف في ذاكرة الهاتف"
                ExportDestination.COMPUTER -> "مشاركة عبر البلوتوث أو WiFi"
                ExportDestination.CLOUD -> "Google Drive أو Dropbox"
                ExportDestination.PRINT -> "إرسال إلى طابعة"
            }, style = MaterialTheme.typography.caption, color = AppThemeColors.textSecondary)
        }
    }
}

class ReportsViewModel : ViewModel() {
    private val _reportData = kotlinx.coroutines.flow.MutableStateFlow(ReportData(ReportSummary(0.0,0.0,0.0,0.0, "", null), SalesData(), ExpensesData(), InventoryData(), null, emptyList()))
    val reportData = _reportData

    private val _savedReports = kotlinx.coroutines.flow.MutableStateFlow<List<com.coffeemaster.app.data.entity.ReportEntity>>(emptyList())
    val savedReports = _savedReports

    fun loadSavedReports(context: android.content.Context) {
        viewModelScope.launch {
            try {
                val repo = com.coffeemaster.app.data.Repository.getInstance(context)
                val list = repo.reportDao.getAll()
                _savedReports.value = list
            } catch (e: Exception) {
                // ignore for now
            }
        }
    }

    // stub for export
    suspend fun exportReport(reportData: ReportData, format: ExportFormat, destination: ExportDestination) {
        // Implement export flow using ExportManager
    }
}
