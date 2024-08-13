package com.example.tech_mart_application.database

import androidx.room.TypeConverter
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product
import com.google.gson.Gson
import java.util.Date


class ProductTypeConvertor {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        if (date == null) {
            return null
        }
        return date.time
    }

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        if (timeStamp == null) {
            return null
        }
        return Date(timeStamp)
    }

    @TypeConverter
    fun fromList(orders: List<Order>?): String? {
        return orders?.joinToString(separator = ",") { order ->
            // Serialize the Order object into a string
            // You can use a JSON library like Gson or Moshi
            Gson().toJson(order)
        }
    }

    @TypeConverter
    fun toList(data: String?): List<Order>? {
        return data?.split(",")?.mapNotNull {
            // Deserialize the string back into an Order object
            Gson().fromJson(it, Order::class.java)
        }
    }

    @TypeConverter
    fun fromListImage(listImage: List<String>?): String? {
        return listImage?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toListImage(image: String?): List<String>? {
        return image?.split(",")
    }

}


