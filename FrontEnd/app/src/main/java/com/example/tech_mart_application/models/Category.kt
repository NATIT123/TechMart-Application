package com.example.tech_mart_application.models

class Category(
    val title: String = "",
    val id: String = "0",
    val picUrl: Int = 0,
    val product: List<Product>? = null
)