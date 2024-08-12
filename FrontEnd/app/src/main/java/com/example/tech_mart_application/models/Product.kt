package com.example.tech_mart_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products")
class Product(
    @PrimaryKey
    var id: String,
    var name: String,
    var description: String,
    var image: Int,
    var price: Double,
    var rating: Double,
    var createdOn: Date,
    var vendor: String
)