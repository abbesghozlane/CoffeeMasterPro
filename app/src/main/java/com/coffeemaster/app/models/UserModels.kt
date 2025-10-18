package com.coffeemaster.app.models

import java.util.Date

sealed class UserRole {
    object OWNER : UserRole() { override fun toString() = "owner" }
    object MANAGER : UserRole() { override fun toString() = "manager" }
    object EMPLOYEE : UserRole() { override fun toString() = "employee" }
    object CASHIER : UserRole() { override fun toString() = "cashier" }
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole,
    val profileImage: String? = null,
    val isActive: Boolean = true,
    val permissions: Set<UserPermission> = emptySet(),
    val salary: Double? = null,
    val hireDate: Date? = null,
    val lastLogin: Date? = null,
    val createdAt: Date = Date()
)

data class UserPermission(
    val id: String,
    val name: String,
    val description: String,
    val category: PermissionCategory
)

enum class PermissionCategory {
    SALES, INVENTORY, FINANCE, REPORTS, EMPLOYEES, SETTINGS, EXPORT
}
