package com.example.data

import kotlinx.coroutines.flow.Flow

class ScanRepository(private val dao: ScanHistoryDao) {
    val allHistory: Flow<List<ScanHistoryEntity>> = dao.getAllHistory()

    suspend fun insert(scan: ScanHistoryEntity) {
        dao.insert(scan)
    }

    suspend fun update(scan: ScanHistoryEntity) {
        dao.update(scan)
    }

    suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
