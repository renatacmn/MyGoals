package br.com.mygoals.base.repository.dao.util

import androidx.room.TypeConverter
import java.util.Date

class CustomDateTypeConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

}
