package com.hadesmori.notes.data.database.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?) : Date?{
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDatestamp(date: Date?) : Long?{
        return date?.time
    }
}