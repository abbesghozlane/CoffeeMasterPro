package com.coffeemaster.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffeemaster.app.data.entity.ReportEntity

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ReportEntity)

    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getById(id: String): ReportEntity?

    @Query("SELECT * FROM reports")
    suspend fun getAll(): List<ReportEntity>
}
