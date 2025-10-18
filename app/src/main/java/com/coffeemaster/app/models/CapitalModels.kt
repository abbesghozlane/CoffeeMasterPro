package com.coffeemaster.app.models

import java.util.Date

data class Capital(
    val id: String,
    val initialAmount: Double,
    val currentAmount: Double,
    val lastUpdated: Date,
    val dailyTransactions: List<CapitalTransaction> = emptyList()
)

data class CapitalTransaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val date: Date,
    val category: TransactionCategory,
    val relatedInvoice: String? = null,
    val paymentMethod: PaymentMethod
)

data class Invoice(
    val id: String,
    val type: InvoiceType,
    val supplier: Supplier,
    val items: List<InvoiceItem>,
    val totalAmount: Double,
    val paidAmount: Double,
    val remainingAmount: Double,
    val issueDate: Date,
    val dueDate: Date,
    val status: InvoiceStatus,
    val notes: String? = null,
    val attachments: List<String> = emptyList()
) {
    val forSaleItems: List<InvoiceItem> get() = items.filter { it.isForSale }
    val consumableItems: List<InvoiceItem> get() = items.filter { !it.isForSale }
}

data class InvoiceItem(
    val productId: String? = null,
    val productName: String,
    val quantity: Double,
    val unit: String,
    val unitPrice: Double,
    val totalPrice: Double,
    val isForSale: Boolean = true
)

// Placeholder enums and classes referenced above
enum class TransactionType { INCOME, EXPENSE, TRANSFER }
enum class TransactionCategory { PRODUCT_PURCHASE, CONSUMABLE_PURCHASE, SALES_INCOME }
enum class InvoiceType { PURCHASE, RETURN }
enum class InvoiceStatus { PENDING, PAID, OVERDUE }

class Supplier(val id: String, val name: String, val defaultPaymentMethod: PaymentMethod)

enum class PaymentMethod { CASH, CARD, TRANSFER }
