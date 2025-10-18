package com.coffeemaster.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coffeemaster.app.data.dao.CapitalDao
import com.coffeemaster.app.data.dao.ReportDao
import com.coffeemaster.app.data.dao.SalesDao
import com.coffeemaster.app.data.entity.CapitalEntity
import com.coffeemaster.app.data.entity.ReportEntity
import com.coffeemaster.app.data.entity.SaleEntity

@Database(entities = [CapitalEntity::class, ReportEntity::class, SaleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun capitalDao(): CapitalDao
    abstract fun reportDao(): ReportDao
    abstract fun salesDao(): SalesDao
}
