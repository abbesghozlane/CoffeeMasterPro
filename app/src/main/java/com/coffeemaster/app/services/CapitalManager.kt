package com.coffeemaster.app.services

import com.coffeemaster.app.data.Repository
import com.coffeemaster.app.data.entity.CapitalEntity
import com.coffeemaster.app.data.entity.SaleEntity
import java.util.Date

class CapitalManager(private val repo: Repository) {

    suspend fun getCurrentCapital(): CapitalEntity? {
        return repo.capitalDao.getCurrent()
    }

    suspend fun processSale(sale: SaleEntity) {
        // insert sale and update capital
        repo.salesDao.insert(sale)

        val current = repo.capitalDao.getCurrent()
        if (current == null) {
            val c = CapitalEntity(id = "capital_1", initialAmount = 0.0, currentAmount = sale.total - sale.cost, lastUpdated = Date().time)
            repo.capitalDao.insert(c)
        } else {
            val updated = current.copy(currentAmount = current.currentAmount + (sale.total - sale.cost), lastUpdated = Date().time)
            repo.capitalDao.insert(updated)
        }
    }
}
