package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ScanHistoryEntity
import com.example.data.ScanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ScanRepository) : ViewModel() {
    val history: StateFlow<List<ScanHistoryEntity>> = repository.allHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addScan(rawValue: String, format: String, type: String) {
        viewModelScope.launch {
            repository.insert(ScanHistoryEntity(rawValue = rawValue, format = format, type = type))
        }
    }

    fun toggleFavorite(scan: ScanHistoryEntity) {
        viewModelScope.launch {
            repository.update(scan.copy(isFavorite = !scan.isFavorite))
        }
    }

    fun deleteScan(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}

class MainViewModelFactory(private val repository: ScanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
