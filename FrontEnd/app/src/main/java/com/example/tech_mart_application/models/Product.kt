package com.example.tech_mart_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products")
class Product(
    @PrimaryKey
    var id: String = "a",
    var name: String = "a",
    var listImage: List<String>? = null,
    var category: Category? = null,
    var description: String = "a",
    var image: Int = 0,
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var color: String = "a",
    var createdOn: Date = Date(),
    var vendor: String = "a",
    var quantity: Int = 0,
)