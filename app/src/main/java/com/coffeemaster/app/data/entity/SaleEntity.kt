package com.coffeemaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class SaleEntity(
    @PrimaryKey val id: String,
    val total: Double,
    val cost: Double,
    val date: Long
)
