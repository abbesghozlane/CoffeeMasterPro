package com.coffeemaster.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeemaster.app.data.Repository
import com.coffeemaster.app.data.entity.SaleEntity
import kotlinx.coroutines.launch
import java.util.UUID

class SalesViewModel(private val context: Context) : ViewModel() {
    fun addSampleSale(total: Double, cost: Double) {
        viewModelScope.launch {
            try {
                val repo = Repository.getInstance(context)
                val sale = SaleEntity(id = UUID.randomUUID().toString(), total = total, cost = cost, date = System.currentTimeMillis())
                    repo.salesDao.insert(sale)
            } catch (e: Exception) {
                // ignore
            }
        }
    }
}
