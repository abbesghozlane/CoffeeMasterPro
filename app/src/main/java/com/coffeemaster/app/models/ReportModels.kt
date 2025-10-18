package com.coffeemaster.app.models

import java.util.Date

data class Report(
    val id: String,
    val type: ReportType,
    val period: ReportPeriod,
    val dateRange: ClosedRange<Date>,
    val data: ReportData,
    val generatedAt: Date = Date(),
    val generatedBy: String
)

data class ReportData(
    val summary: ReportSummary,
    val sales: SalesData,
    val expenses: ExpensesData,
    val inventory: InventoryData,
    val employees: EmployeesData? = null,
    val charts: List<ChartData> = emptyList()
)

data class ReportSummary(
    val totalRevenue: Double,
    val totalExpenses: Double,
    val netProfit: Double,
    val profitMargin: Double,
    val bestSellingProduct: String,
    val bestPerformingEmployee: String?
)

enum class ReportType {
    DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM
}

enum class ReportPeriod {
    TODAY, YESTERDAY, LAST_7_DAYS, LAST_30_DAYS, THIS_MONTH, LAST_MONTH, CUSTOM_RANGE
}

enum class ExportFormat {
    PDF, DOCX, EXCEL, HTML
}

enum class ExportDestination {
    DEVICE, COMPUTER, CLOUD, PRINT
}

// Minimal placeholder types for data sections
class SalesData
class ExpensesData
class InventoryData
class EmployeesData
class ChartData
