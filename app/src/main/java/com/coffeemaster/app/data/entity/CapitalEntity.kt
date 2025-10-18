package com.coffeemaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "capital")
data class CapitalEntity(
    @PrimaryKey val id: String,
    val initialAmount: Double,
    val currentAmount: Double,
    val lastUpdated: Long
)
