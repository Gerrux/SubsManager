package com.example.testsubsmanager.database.utils

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun toDatabaseValue(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun toEntityValue(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }
}