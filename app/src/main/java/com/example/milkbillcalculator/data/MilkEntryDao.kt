package com.example.milkbillcalculator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MilkEntryDao {
    @Query("SELECT * FROM milk_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<MilkEntry>>

    @Query("SELECT * FROM milk_entries WHERE date = :date")
    suspend fun getEntryForDate(date: LocalDate): MilkEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: MilkEntry)

    @Update
    suspend fun update(entry: MilkEntry)

    @Delete
    suspend fun delete(entry: MilkEntry)

    @Query("UPDATE milk_entries SET isDelivered = :isDelivered WHERE date = :date")
    suspend fun updateDeliveryStatus(date: LocalDate, isDelivered: Boolean)
}
