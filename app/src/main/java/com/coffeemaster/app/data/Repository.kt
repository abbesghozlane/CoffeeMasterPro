package com.coffeemaster.app.data

import android.content.Context
import androidx.room.Room
import com.coffeemaster.app.data.dao.CapitalDao
import com.coffeemaster.app.data.dao.ReportDao
import com.coffeemaster.app.data.dao.SalesDao

class Repository private constructor(context: Context) {
    private val db: AppDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "coffeemaster.db").build()

    val capitalDao: CapitalDao get() = db.capitalDao()
    val reportDao: ReportDao get() = db.reportDao()
    val salesDao: SalesDao get() = db.salesDao()
    // expose db for advanced operations when necessary
    val dbInstance: AppDatabase get() = db

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context): Repository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Repository(context).also { INSTANCE = it }
        }
    }
}
