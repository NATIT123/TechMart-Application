package com.example.tech_mart_application.models

class Order(
    var id: String? = null,
    var subTotal: Double,
    var total: Double,
    var transportPrice: Double,
    var tax: Double,
    var discount: Double,
    var listProduct: List<Product>? = null
)