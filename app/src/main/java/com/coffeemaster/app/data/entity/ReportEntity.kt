package com.coffeemaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey val id: String,
    val type: String,
    val period: String,
    val payloadJson: String,
    val generatedAt: Long,
    val generatedBy: String
)
