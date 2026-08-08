package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rawValue: String,
    val format: String,
    val type: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
