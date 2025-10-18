package com.coffeemaster.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffeemaster.app.data.entity.SaleEntity

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: SaleEntity)

    @Query("SELECT * FROM sales ORDER BY date DESC")
    suspend fun getAll(): List<SaleEntity>
}
