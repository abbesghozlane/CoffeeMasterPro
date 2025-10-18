package com.coffeemaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { onNavigate("sales") }, modifier = Modifier.fillMaxWidth().padding(4.dp)) { Text("مبيعات") }
        Button(onClick = { onNavigate("inventory") }, modifier = Modifier.fillMaxWidth().padding(4.dp)) { Text("المخزون") }
        Button(onClick = { onNavigate("employees") }, modifier = Modifier.fillMaxWidth().padding(4.dp)) { Text("الموظفين") }
        Button(onClick = { onNavigate("capital") }, modifier = Modifier.fillMaxWidth().padding(4.dp)) { Text("رأس المال") }
        Button(onClick = { onNavigate("reports") }, modifier = Modifier.fillMaxWidth().padding(4.dp)) { Text("التقارير") }
    }
}
