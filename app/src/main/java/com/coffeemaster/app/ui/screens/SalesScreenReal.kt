package com.coffeemaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.coffeemaster.app.data.Repository
import com.coffeemaster.app.data.entity.SaleEntity
import java.util.UUID
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun SalesScreenReal() {
    val ctx = LocalContext.current
    val repo = Repository.getInstance(ctx)
    val scope = rememberCoroutineScope()

    var totalText by remember { mutableStateOf("") }
    var costText by remember { mutableStateOf("") }
    var sales by remember { mutableStateOf<List<SaleEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                sales = repo.salesDao.getAll()
            } catch (_: Exception) {}
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = totalText, onValueChange = { totalText = it }, label = { Text("Total") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = costText, onValueChange = { costText = it }, label = { Text("Cost") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val total = totalText.toDoubleOrNull() ?: 0.0
            val cost = costText.toDoubleOrNull() ?: 0.0
            val sale = SaleEntity(id = UUID.randomUUID().toString(), total = total, cost = cost, date = System.currentTimeMillis())
            scope.launch {
                try {
                    // process sale via CapitalManager to update capital as well
                    val capitalManager = com.coffeemaster.app.services.CapitalManager(repo)
                    capitalManager.processSale(sale)
                    // update list
                    sales = repo.salesDao.getAll()
                } catch (_: Exception) {}
            }
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Add Sale")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Recent Sales:")
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sales) { s ->
                Text("${s.id.take(6)} - Total: ${s.total} - Cost: ${s.cost}")
            }
        }
    }
}
