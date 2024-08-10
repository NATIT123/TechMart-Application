package com.example.tech_mart_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class Product(
    @PrimaryKey
    var id: String
)