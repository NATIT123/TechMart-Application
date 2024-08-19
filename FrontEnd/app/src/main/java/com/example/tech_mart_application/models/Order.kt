package com.example.tech_mart_application.models

class Order(
    var id: String? = null,
    var subTotal: Double = 0.0,
    var total: Double = 0.0,
    var transportPrice: Double = 0.0,
    var tax: Double = 0.0,
    var discount: Double = 0.0,
    var listItem: List<Items>? = null,
    var orderStatus: String = "1",
    var statusDate: String = "1",
    var category: Category? = null,
    var user: User? = null
)