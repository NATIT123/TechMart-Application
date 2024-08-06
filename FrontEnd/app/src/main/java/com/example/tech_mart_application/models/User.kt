package com.example.tech_mart_application.models

class User(
    var id: String? = null,
    var image: String,
    var email: String,
    var phone: String,
    var fullName: String,
    var password: ByteArray
)