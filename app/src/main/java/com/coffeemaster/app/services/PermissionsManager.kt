package com.coffeemaster.app.services

import com.coffeemaster.app.models.*

object PermissionsManager {

    val OWNER_PERMISSIONS: Set<UserPermission> = setOf(
        UserPermission("export_reports", "تصدير التقارير", "تصدير التقارير لصيغ مختلفة", PermissionCategory.EXPORT),
        UserPermission("print_reports", "طباعة التقارير", "طباعة التقارير مباشرة", PermissionCategory.EXPORT),
        UserPermission("backup_data", "نسخ احتياطي", "عمل نسخ احتياطية للبيانات", PermissionCategory.EXPORT),
        UserPermission("reports_full", "تقارير كاملة", "الوصول لجميع التقارير", PermissionCategory.REPORTS),
        UserPermission("finance_manage", "إدارة مالية كاملة", "إدارة جميع الجوانب المالية", PermissionCategory.FINANCE),
        UserPermission("employees_manage", "إدارة موظفين", "إدارة كاملة للموظفين", PermissionCategory.EMPLOYEES)
    )

    val EMPLOYEE_PERMISSIONS: Set<UserPermission> = setOf(
        UserPermission("sales_create", "إنشاء مبيعات", "عمل عمليات بيع جديدة", PermissionCategory.SALES),
        UserPermission("sales_view_own", "عرض مبيعاتي", "عرض المبيعات الخاصة بي", PermissionCategory.SALES),
        UserPermission("inventory_view", "عرض المخزون", "عرض المنتجات المتاحة", PermissionCategory.INVENTORY)
    )

    fun canExportReports(user: User): Boolean {
        return user.role is UserRole.OWNER || user.permissions.any { it.id == "export_reports" }
    }

    fun canPrintReports(user: User): Boolean {
        return user.role is UserRole.OWNER || user.permissions.any { it.id == "print_reports" }
    }
}
