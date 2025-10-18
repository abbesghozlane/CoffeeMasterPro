package com.coffeemaster.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffeemaster.app.data.entity.CapitalEntity

@Dao
interface CapitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(capital: CapitalEntity)

    @Query("SELECT * FROM capital LIMIT 1")
    suspend fun getCurrent(): CapitalEntity?
}
