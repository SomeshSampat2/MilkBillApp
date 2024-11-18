package com.example.milkbillcalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class MilkType {
    COW,
    BUFFALO
}

@Entity(tableName = "milk_entries")
data class MilkEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val milkType: MilkType,
    val litres: Double,
    val pricePerLitre: Double,
    val isDelivered: Boolean = false
) {
    val totalPrice: Double
        get() = litres * pricePerLitre
}
