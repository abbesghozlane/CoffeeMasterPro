package com.coffeemaster.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SalesScreen() {
    // Simple sales screen placeholder
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("شاشة المبيعات - اذهب الى Home ثم اضغط مبيعات لإضافة أمثلة")
    }
}

@Composable
fun InventoryScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("شاشة المخزون - قيد التطوير")
    }
}

@Composable
fun EmployeesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("شاشة الموظفين - قيد التطوير")
    }
}

@Composable
fun CapitalScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("شاشة رأس المال - قيد التطوير")
    }
}
