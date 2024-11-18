package com.example.milkbillcalculator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milkbillcalculator.data.MilkEntry
import com.example.milkbillcalculator.data.MilkEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MilkEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MilkEntryRepository = MilkEntryRepository(application)
    val allEntries: Flow<List<MilkEntry>> = repository.getAllEntries()

    fun insertEntry(entry: MilkEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(entry)
        }
    }

    fun updateEntry(entry: MilkEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(entry)
        }
    }

    fun deleteEntry(entry: MilkEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(entry)
        }
    }

    fun updateDeliveryStatus(entry: MilkEntry, isDelivered: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(entry.copy(isDelivered = isDelivered))
        }
    }
}
