package com.coffeemaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.coffeemaster.app.data.Repository
import kotlinx.coroutines.launch

@Composable
fun CapitalScreenReal() {
    val ctx = LocalContext.current
    val repo = Repository.getInstance(ctx)
    val scope = rememberCoroutineScope()

    var capital by remember { mutableStateOf<com.coffeemaster.app.data.entity.CapitalEntity?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                capital = repo.capitalDao.getCurrent()
            } catch (_: Exception) {}
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.Start) {
        Text("رأس المال الحالي:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${capital?.currentAmount ?: 0.0}", modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("تاريخ آخر تحديث: ${capital?.lastUpdated ?: "-"}")
    }
}
