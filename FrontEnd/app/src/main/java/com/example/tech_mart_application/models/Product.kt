package com.example.tech_mart_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products")
class Product(
    @PrimaryKey
    var id: String="",
    var name: String = "",
    var listImage: List<String>? = null,
    var description: String = "",
    var image: Int = 0,
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var color: String = "",
    var createdOn: Date = Date(),
    var vendor: String = "",
    var quantity: Int = 0,
    var listOrder: List<Order>? = null
)