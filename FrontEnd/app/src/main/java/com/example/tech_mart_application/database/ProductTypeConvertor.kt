package com.example.tech_mart_application.database

import androidx.room.TypeConverter
import java.util.Date


class ProductTypeConvertor {

    @TypeConverter
    fun fromDate(date: Date): String {
        return "1"
    }

    @TypeConverter()
    fun toDate(date: String): Date {
        return Date()
    }
}