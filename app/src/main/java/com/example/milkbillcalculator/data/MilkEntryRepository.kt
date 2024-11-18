package com.example.milkbillcalculator.data

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class MilkEntryRepository(application: Application) {
    private val database: AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "milk_entries_database"
    ).build()
    
    private val milkEntryDao = database.milkEntryDao()

    fun getAllEntries(): Flow<List<MilkEntry>> = milkEntryDao.getAllEntries()

    suspend fun getEntryForDate(date: LocalDate): MilkEntry? = milkEntryDao.getEntryForDate(date)

    suspend fun insert(entry: MilkEntry) = milkEntryDao.insert(entry)

    suspend fun update(entry: MilkEntry) = milkEntryDao.update(entry)

    suspend fun delete(entry: MilkEntry) = milkEntryDao.delete(entry)

    suspend fun updateDeliveryStatus(date: LocalDate, isDelivered: Boolean) = milkEntryDao.updateDeliveryStatus(date, isDelivered)
}
