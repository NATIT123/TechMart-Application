package com.example.tech_mart_application.models

class User(
    var id: String? = null,
    var image: String="",
    var email: String="",
    var phone: String="",
    var fullName: String="",
    var password: String="",
    var address: String="",
    var role: String="",
    var listOrder: List<Order>? = null,
    var tokenMoMo: String? = null
)