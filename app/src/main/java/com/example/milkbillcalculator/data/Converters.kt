package com.example.milkbillcalculator.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromMilkType(value: String): MilkType {
        return enumValueOf(value)
    }

    @TypeConverter
    fun toMilkType(milkType: MilkType): String {
        return milkType.name
    }
}
